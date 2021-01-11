package com.example.docksapi.doc;

import com.example.docksapi.exception.exceptions.DocNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DocServiceImpl implements DocService {

    private final DocRepository docRepository;
    private final DocMapper docMapper;

    @Override
    public List<DocDTO> getDocList() {
        return docMapper.toDocDTOs(docRepository.findAll());
    }

    @Override
    public DocDTO getDocById(Long id) {
        return docMapper.toDocDTO(docRepository.findById(id).orElseThrow(DocNotFoundException::new));
    }

    @Override
    public DocDTO saveDoc(Long id, DocDTO docDTO) {
        Doc doc = docMapper.toDoc(docDTO);
        if (id != null) doc.setId(id);
        return docMapper.toDocDTO(docRepository.save(doc));
    }

    @Override
    public void deleteDoc(Long id) {
        docRepository.deleteById(id);
    }
}
