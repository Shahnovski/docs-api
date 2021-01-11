package com.example.docksapi.doc;

import com.example.docksapi.common.ApplicationProperties;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(ApplicationProperties.DOC_API_URL)
public class DocController {

    private final DocService docService;

    @GetMapping(value = "", produces = "application/json; charset=UTF-8")
    List<DocDTO> getDocList() {
        return docService.getDocList();
    }

    @GetMapping("/{id}")
    DocDTO getDocById(@PathVariable(value = "id") Long docId) {
        return docService.getDocById(docId);
    }

    @PostMapping("")
    DocDTO createDoc(@Valid @RequestBody DocDTO docDTO) {
        return docService.saveDoc(null, docDTO);
    }

    @PutMapping("/{id}")
    public DocDTO updateDoc(@PathVariable(value = "id") Long docId, @Valid @RequestBody DocDTO docDTO) {
        return docService.saveDoc(docId, docDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteDoc(@PathVariable(value = "id") Long docId) {
        docService.deleteDoc(docId);
    }
}
