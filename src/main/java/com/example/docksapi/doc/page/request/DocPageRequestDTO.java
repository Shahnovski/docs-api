package com.example.docksapi.doc.page.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocPageRequestDTO {
    Integer page;
    Integer size;
    String sort;
    String filter;
}
