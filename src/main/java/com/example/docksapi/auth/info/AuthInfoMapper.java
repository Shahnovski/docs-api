package com.example.docksapi.auth.info;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthInfoMapper {

    AuthInfoDTO toAuthInfoDTO(AuthInfo authInfo);

    AuthInfo toAuthInfo(AuthInfoDTO authInfoDTO);
}
