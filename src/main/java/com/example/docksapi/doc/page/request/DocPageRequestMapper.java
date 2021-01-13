package com.example.docksapi.doc.page.request;

import com.example.docksapi.doc.Doc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocPageRequestMapper {

    @Mapping(source = "page", target = "pageNumber")
    @Mapping(source = "size", target = "pageSize")
    @Mapping(source = "sort", target = "sortByKey")
    @Mapping(source = "filter", target = "filterString")
    DocPageRequest toDocPageRequest(DocPageRequestDTO docPageRequestDTO);

    List<Doc> toDocPageRequestDTOs(List<Doc> docPageRequests);

    DocPageRequestDTO toDocPageRequestDTO(DocPageRequest docPageRequest);

}
