package com.example.docksapi.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doc")
public class Doc {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "docName", nullable = false)
    @NotBlank(message = "DocName should not be empty")
    @Size(min = 5, max = 20, message = "DocName should be between 5 and 20 characters")
    private String docName;

    @Column(name = "docCreationDate", nullable = false)
    @NotNull(message = "DocCreationDate should not be empty")
    private Date docCreationDate;

    @Column(name = "docDeadlineDate", nullable = false)
    @NotNull(message = "DocDeadlineDate should not be empty")
    private Date docDeadlineDate;

    @Column(name = "docStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "DocStatus should not be empty")
    private DocStatus docStatus;

    @Column(name = "docAuthorName", nullable = false)
    private String docAuthorName;

    @Column(name = "docClientName", nullable = false)
    @NotBlank(message = "DocClientName should not be empty")
    @Size(min = 5, max = 50, message = "DocClientName should be between 5 and 50 characters")
    private String docClientName;

    @Column(name = "docWorkerName", nullable = false)
    @NotBlank(message = "DocWorkerName should not be empty")
    @Size(min = 5, max = 50, message = "DocWorkerName should be between 5 and 50 characters")
    private String docWorkerName;

    @Column(name = "docFileName", nullable = false)
    @NotBlank(message = "DocFileName should not be empty")
    @Size(min = 5, max = 20, message = "DocFileName should be between 5 and 20 characters")
    private String docFileName;

    @Lob
    @Column(name = "docFileContent", nullable = false)
    @NotNull(message = "DocFileContent should not be empty")
    @Size(min = 1, message = "DocFileContent should not be empty")
    private byte[] docFileContent;

}
