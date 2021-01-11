package com.example.docksapi.doc;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocMapper {

    DocDTO toDocDTO(Doc doc);

    List<DocDTO> toDocDTOs(List<Doc> docs);

    Doc toDoc(DocDTO docDTO);
}
