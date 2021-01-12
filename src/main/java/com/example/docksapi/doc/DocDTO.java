package com.example.docksapi.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocDTO {

    private Long id;

    @NotBlank(message = "DocName should not be empty")
    @Size(min = 5, max = 20, message = "DocName should be between 5 and 20 characters")
    private String docName;

    @NotNull(message = "DocCreationDate should not be empty")
    private Date docCreationDate;

    @NotNull(message = "DocDeadlineDate should not be empty")
    private Date docDeadlineDate;

    private DocStatus docStatus;

    private String docAuthorName;

    @NotBlank(message = "DocClientName should not be empty")
    @Size(min = 5, max = 50, message = "DocClientName should be between 5 and 50 characters")
    private String docClientName;

    @NotBlank(message = "DocWorkerName should not be empty")
    @Size(min = 5, max = 50, message = "DocWorkerName should be between 5 and 50 characters")
    private String docWorkerName;

    @NotBlank(message = "DocFileName should not be empty")
    @Size(min = 5, max = 20, message = "DocFileName should be between 5 and 20 characters")
    private String docFileName;

    @NotNull(message = "DocFileContent should not be empty")
    @Size(min = 1, message = "DocFileContent should not be empty")
    private byte[] docFileContent;
}
