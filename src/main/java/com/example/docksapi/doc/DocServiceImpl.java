package com.example.docksapi.doc;

import com.example.docksapi.auth.info.AuthInfoService;
import com.example.docksapi.exception.exceptions.DocNotFoundException;
import com.example.docksapi.exception.exceptions.UserNotFoundException;
import com.example.docksapi.user.Role;
import com.example.docksapi.user.User;
import com.example.docksapi.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DocServiceImpl implements DocService {

    private final DocRepository docRepository;
    private final UserRepository userRepository;
    private final AuthInfoService authInfoService;
    private final DocMapper docMapper;

    @Override
    public List<DocDTO> getDocList(Authentication authentication) {
        return docMapper.toDocDTOs(docRepository.findAll());
    }

    @Override
    public DocDTO getDocById(Long id, Authentication authentication) {
        return docMapper.toDocDTO(docRepository.findById(id).orElseThrow(DocNotFoundException::new));
    }

    @Override
    public DocDTO saveDoc(Long id, DocDTO docDTO, Authentication authentication) {
        Doc modifiedDoc = docMapper.toDoc(docDTO);
        if (id != null) {
            modifiedDoc.setId(id);
            Doc doc = docRepository.findById(id).orElseThrow(DocNotFoundException::new);
            modifiedDoc.setDocAuthor(doc.getDocAuthor());
            if (!allowEditDoc(authentication, modifiedDoc)) {
                throw new AccessDeniedException("The document does not belong to this user");
            }
        }
        else {
            modifiedDoc.setDocAuthor(authInfoService.getUserByAuthentication(authentication));
        }
        return docMapper.toDocDTO(docRepository.save(modifiedDoc));
    }

    @Override
    public void deleteDoc(Long id, Authentication authentication) {
        Doc doc = docRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (!allowEditDoc(authentication, doc)) {
            throw new AccessDeniedException("The document does not belong to this user");
        }
        docRepository.deleteById(id);
    }

    private boolean allowEditDoc(Authentication authentication, Doc doc) {
        User currentUser = authInfoService.getUserByAuthentication(authentication);
        return doc.getDocAuthor().getId().equals(currentUser.getId()) || currentUser.getRoles().contains(Role.ADMIN);
    }
}
