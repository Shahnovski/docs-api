package com.example.docksapi.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocDTO {
    private Long id;
    private String docName;
    private Date docCreationDate;
    private Date docDeadlineDate;
    private DocStatus docStatus;
    private String docAuthorName;
    private String docClientName;
    private String docWorkerName;
    private byte[] docFile;
}
