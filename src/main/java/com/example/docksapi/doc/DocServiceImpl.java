package com.example.docksapi.doc;

import com.example.docksapi.auth.info.AuthInfoService;
import com.example.docksapi.common.SearchCriteria;
import com.example.docksapi.doc.page.DocPage;
import com.example.docksapi.doc.page.DocPageDTO;
import com.example.docksapi.doc.page.DocPageMapper;
import com.example.docksapi.doc.page.request.DocPageRequest;
import com.example.docksapi.doc.page.request.DocPageRequestDTO;
import com.example.docksapi.doc.page.request.DocPageRequestMapper;
import com.example.docksapi.exception.exceptions.DocNotFoundException;
import com.example.docksapi.exception.exceptions.UserNotFoundException;
import com.example.docksapi.user.Role;
import com.example.docksapi.user.User;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
@AllArgsConstructor
public class DocServiceImpl implements DocService {

    private final DocRepository docRepository;
    private final AuthInfoService authInfoService;
    private final DocMapper docMapper;
    private final DocPageRequestMapper docPageRequestMapper;
    private final DocPageMapper docPageMapper;

    @Override
    public DocPageDTO getDocList(DocPageRequestDTO docPageRequestDTO, Authentication authentication) {
        if (docPageRequestDTO.getPage() == null) {
            docPageRequestDTO.setPage(0);
        }
        if (docPageRequestDTO.getSize() == null) {
            docPageRequestDTO.setSize(15);
        }
        if (docPageRequestDTO.getSort() == null) {
            docPageRequestDTO.setSort("id");
        }
        DocPageRequest docPageRequest = docPageRequestMapper.toDocPageRequest(docPageRequestDTO);
        Pageable pageable = PageRequest.of(
                docPageRequest.getPageNumber(),
                docPageRequest.getPageSize(),
                Sort.by(docPageRequest.getSortByKey())
        );
        Specification specification = null;
        if (docPageRequest.getFilterString() != null) {
            JSONArray filterJsonArray = new JSONArray(docPageRequest.getFilterString());
            DocSpecification[] docSpecifications = new DocSpecification[filterJsonArray.length()];
            try {
                for (int i = 0; i < filterJsonArray.length(); i++) {
                    String filterKey = filterJsonArray.getJSONArray(i).get(0).toString();
                    Object filterValue = filterJsonArray.getJSONArray(i).get(1).toString();
                    String operation = ":";
                    if (filterKey.compareTo("docStatus") == 0) {
                        filterValue = DocStatus.valueOf((String) filterValue);
                    } else if (filterKey.compareTo("docCreationDate") == 0) {
                        filterValue = new SimpleDateFormat("yyyy-MM-dd").parse((String) filterValue);
                    } else if (filterKey.compareTo("docDeadlineDate") == 0) {
                        filterValue = new SimpleDateFormat("yyyy-MM-dd").parse((String) filterValue);
                    }
                    docSpecifications[i] = new DocSpecification(new SearchCriteria(filterKey, operation, (Object) filterValue));
                    if (specification == null) specification = Specification.where(docSpecifications[i]);
                    else specification = specification.and(docSpecifications[i]);
                }
            }
            catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
        }
        DocSpecification isDeleteSpecification = new DocSpecification(new SearchCriteria("isDeleted",":",false));
        if (specification == null) specification = Specification.where(isDeleteSpecification);
        else specification = specification.and(isDeleteSpecification);
        Page page = docRepository.findAll(specification, pageable);
        DocPage docPage = DocPage.builder()
                .docs(docMapper.toDocDTOs(page.getContent()))
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
        return docPageMapper.toDocPageDTO(docPage);
    }

    @Override
    public DocDTO getDocById(Long id, Authentication authentication) {
        Doc doc = docRepository.findById(id).orElseThrow(DocNotFoundException::new);
        if (doc.getIsDeleted()) {
            throw new DocNotFoundException();
        }
        return docMapper.toDocDTO(doc);
    }

    @Override
    public DocDTO saveDoc(Long id, DocDTO docDTO, Authentication authentication) {
        Doc modifiedDoc = docMapper.toDoc(docDTO);
        if (id != null) {
            modifiedDoc.setId(id);
            Doc doc = docRepository.findById(id).orElseThrow(DocNotFoundException::new);
            if (doc.getIsDeleted()) {
                throw new DocNotFoundException();
            }
            modifiedDoc.setDocAuthor(doc.getDocAuthor());
            if (!allowEditDoc(authentication, modifiedDoc)) {
                throw new AccessDeniedException("The document does not belong to this user");
            }
        }
        else {
            modifiedDoc.setDocAuthor(authInfoService.getUserByAuthentication(authentication));
        }
        modifiedDoc.setIsDeleted(false);
        return docMapper.toDocDTO(docRepository.save(modifiedDoc));
    }

    @Override
    public void deleteDoc(Long id, Authentication authentication) {
        Doc doc = docRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (doc.getIsDeleted()) {
            throw new DocNotFoundException();
        }
        if (!allowEditDoc(authentication, doc)) {
            throw new AccessDeniedException("The document does not belong to this user");
        }
        doc.setIsDeleted(true);
        docRepository.save(doc);
    }

    private boolean allowEditDoc(Authentication authentication, Doc doc) {
        User currentUser = authInfoService.getUserByAuthentication(authentication);
        return doc.getDocAuthor().getId().equals(currentUser.getId()) || currentUser.getRoles().contains(Role.ADMIN);
    }
}
