package com.example.docksapi.doc;

import com.example.docksapi.common.ApplicationProperties;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
