package com.example.docksapi.exception;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Data
public class ExceptionMessages {

    @Value("${exception.DocNotFound.message}")
    String docNotFoundMessage;

    @Value("${exception.UserNotFound.message}")
    String userNotFoundMessage;



    @Value("${exception.ParseException.message}")
    String parseExceptionMessage;

    @Value("${exception.NullPointerException.message}")
    String NullPointerExceptionMessage;

    @Value("${exception.IOException.message}")
    String IOExceptionMessage;

    @Value("${exception.NotAuthorizedException.message}")
    String NotAuthorizedExceptionMessage;

    @Value("${exception.PropertyReferenceException.message}")
    String PropertyReferenceExceptionMessage;

    @Value("${exception.JSONExceptionMessage.message}")
    String JSONExceptionMessage;

}
