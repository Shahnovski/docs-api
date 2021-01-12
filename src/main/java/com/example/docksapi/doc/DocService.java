package com.example.docksapi.doc;

import org.springframework.security.core.Authentication;

import java.util.List;

public interface DocService {

    List<DocDTO> getDocList(Authentication authentication);

    DocDTO getDocById(Long id, Authentication authentication);

    DocDTO saveDoc(Long id, DocDTO docDTO, Authentication authentication);

    void deleteDoc(Long id, Authentication authentication);
}
