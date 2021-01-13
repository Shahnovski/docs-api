package com.example.docksapi.doc.page;

import com.example.docksapi.doc.Doc;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocPageMapper {

    DocPageDTO toDocPageDTO(DocPage docPage);

    List<Doc> toDocPageDTOs(List<Doc> docPages);

    DocPage toDocPage(DocPageDTO docPageDTO);
}
