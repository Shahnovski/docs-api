package com.example.docksapi.unit.doc;

import com.example.docksapi.auth.info.AuthInfoService;
import com.example.docksapi.doc.*;
import com.example.docksapi.doc.page.DocPageMapper;
import com.example.docksapi.doc.page.request.DocPageRequestMapper;
import com.example.docksapi.user.Role;
import com.example.docksapi.user.User;
import com.example.docksapi.user.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DocServiceTest {

    private static final Doc DOC = mock(Doc.class);
    private static final User USER = mock(User.class);
    private static final User AUTHOR = mock(User.class);
    private static final DocDTO DOC_DTO = mock(DocDTO.class);
    private static final Authentication AUTHENTICATION = mock(Authentication.class);

    private final DocRepository docRepository = mock(DocRepository.class);
    private final AuthInfoService authInfoService = mock(AuthInfoService.class);
    private final DocPageRequestMapper docPageRequestMapper = mock(DocPageRequestMapper.class);
    private final DocPageMapper docPageMapper = mock(DocPageMapper.class);
    private final DocMapper docMapper = mock(DocMapper.class);

    private final DocService docService = new DocServiceImpl(
            docRepository,
            authInfoService,
            docMapper,
            docPageRequestMapper,
            docPageMapper
    );

    @Test
    public void adminShouldAlwaysEditToAnyCar() {
        final Long USER_ID = 1L;
        final Long DOC_AUTHOR_ID = 2L;
        when(authInfoService.getUserByAuthentication(AUTHENTICATION)).thenReturn(USER);
        when(USER.getRoles()).thenReturn(Collections.singleton(Role.ADMIN));
        when(DOC.getDocAuthor()).thenReturn(AUTHOR);
        when(AUTHOR.getId()).thenReturn(DOC_AUTHOR_ID);
        when(USER.getId()).thenReturn(USER_ID);
        boolean editAllowed = docService.allowEditDoc(AUTHENTICATION, DOC);
        assertThat(editAllowed).isTrue();
    }

    @Test
    public void userShouldEditToTheirCar() {
        final Long USER_ID = 1L;
        final Long DOC_AUTHOR_ID = 1L;
        when(authInfoService.getUserByAuthentication(AUTHENTICATION)).thenReturn(USER);
        when(USER.getRoles()).thenReturn(Collections.singleton(Role.USER));
        when(DOC.getDocAuthor()).thenReturn(AUTHOR);
        when(AUTHOR.getId()).thenReturn(DOC_AUTHOR_ID);
        when(USER.getId()).thenReturn(USER_ID);
        boolean accessAllowed = docService.allowEditDoc(AUTHENTICATION, DOC);
        assertThat(accessAllowed).isTrue();
    }

    @Test
    public void userShouldNotEditToSomeoneCar() {
        final Long USER_ID = 1L;
        final Long DOC_AUTHOR_ID = 2L;
        when(authInfoService.getUserByAuthentication(AUTHENTICATION)).thenReturn(USER);
        when(USER.getRoles()).thenReturn(Collections.singleton(Role.USER));
        when(DOC.getDocAuthor()).thenReturn(AUTHOR);
        when(AUTHOR.getId()).thenReturn(DOC_AUTHOR_ID);
        when(USER.getId()).thenReturn(USER_ID);
        boolean accessAllowed = docService.allowEditDoc(AUTHENTICATION, DOC);
        assertThat(accessAllowed).isFalse();
    }

    @Test
    public void getDocByIdTest() {
        final Long DOC_ID = 1L;
        when(docRepository.findById(DOC_ID)).thenReturn(java.util.Optional.ofNullable(DOC));
        assert DOC != null;
        when(DOC.getIsDeleted()).thenReturn(false);
        when(docMapper.toDocDTO(DOC)).thenReturn(DOC_DTO);
        DocDTO docDTO = docService.getDocById(DOC_ID, AUTHENTICATION);
        Assert.assertNotNull(docDTO);
    }

}
