package com.example.docksapi.doc.page;

import com.example.docksapi.doc.DocDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocPageDTO {
    List<DocDTO> docs;
    Long totalElements;
    Integer totalPages;
}
