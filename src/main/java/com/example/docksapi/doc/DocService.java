package com.example.docksapi.doc;

import java.util.List;

public interface DocService {

    List<DocDTO> getDocList();

    DocDTO getDocById(Long id);

    DocDTO saveDoc(Long id, DocDTO docDTO);

    void deleteDoc(Long id);
}
