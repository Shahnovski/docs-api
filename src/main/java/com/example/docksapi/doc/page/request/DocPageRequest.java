package com.example.docksapi.doc.page.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocPageRequest {
    Integer pageNumber;
    Integer pageSize;
    String sortByKey;
    String filterString;
}
