package com.example.docksapi.doc;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocMapper {

    @Mapping(source = "docAuthor.id", target = "docAuthorId")
    @Mapping(source = "docAuthor.username", target = "docAuthorName")
    DocDTO toDocDTO(Doc doc);

    List<DocDTO> toDocDTOs(List<Doc> docs);

    Doc toDoc(DocDTO docDTO);
}
