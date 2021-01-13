package com.example.docksapi.doc;

import com.example.docksapi.common.ApplicationProperties;
import com.example.docksapi.doc.page.DocPageDTO;
import com.example.docksapi.doc.page.request.DocPageRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(ApplicationProperties.DOC_API_URL)
public class DocController {

    private final DocService docService;

    @RolesAllowed({"ADMIN", "USER"})
    @GetMapping(value = "", produces = "application/json; charset=UTF-8")
    DocPageDTO getDocList(DocPageRequestDTO docPageRequestDTO, Authentication authentication) {
        return docService.getDocList(docPageRequestDTO, authentication);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @GetMapping("/{id}")
    DocDTO getDocById(@PathVariable(value = "id") Long docId, Authentication authentication) {
        return docService.getDocById(docId, authentication);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @PostMapping("")
    DocDTO createDoc(@Valid @RequestBody DocDTO docDTO, Authentication authentication) {
        return docService.saveDoc(null, docDTO, authentication);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @PutMapping("/{id}")
    public DocDTO updateDoc(@PathVariable(value = "id") Long docId,
                            @Valid @RequestBody DocDTO docDTO,
                            Authentication authentication) {
        return docService.saveDoc(docId, docDTO, authentication);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @DeleteMapping("/{id}")
    public void deleteDoc(@PathVariable(value = "id") Long docId, Authentication authentication) {
        docService.deleteDoc(docId, authentication);
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
