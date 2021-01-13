package com.example.docksapi.doc;

import com.example.docksapi.doc.page.DocPageDTO;
import com.example.docksapi.doc.page.request.DocPageRequestDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface DocService {

    DocPageDTO getDocList(DocPageRequestDTO docPageRequestDTO, Authentication authentication);

    DocDTO getDocById(Long id, Authentication authentication);

    DocDTO saveDoc(Long id, DocDTO docDTO, Authentication authentication);

    void deleteDoc(Long id, Authentication authentication);

    boolean allowEditDoc(Authentication authentication, Doc doc);
}
