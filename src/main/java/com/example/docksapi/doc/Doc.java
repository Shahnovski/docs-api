package com.example.docksapi.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;
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
    private String docName;

    @Column(name = "docCreationDate", nullable = false)
    private Date docCreationDate;

    @Column(name = "docDeadlineDate", nullable = false)
    private Date docDeadlineDate;

    @Column(name = "docStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private DocStatus docStatus;

    @Column(name = "docAuthorName", nullable = false)
    private String docAuthorName;

    @Column(name = "docClientName", nullable = false)
    private String docClientName;

    @Column(name = "docWorkerName", nullable = false)
    private String docWorkerName;

    @Lob
    @Column(name = "docFile", nullable = false)
    private byte[] docFile;

}
