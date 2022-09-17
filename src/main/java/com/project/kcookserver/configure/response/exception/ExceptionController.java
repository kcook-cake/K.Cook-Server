package com.project.kcookserver.configure.response.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RequiredArgsConstructor
@RestController
public class ExceptionController {

    @GetMapping(value = "/errors/not-authenticated-account")
    public void getNotAuthenticatedAccountException() {
        throw new CustomException(CustomExceptionStatus.NOT_AUTHENTICATED_ACCOUNT);
    }

    @GetMapping(value = "/errors/invalid-jwt")
    public void getInvalidJwtException() {
        throw new CustomException(CustomExceptionStatus.INVALID_JWT);
    }

    @GetMapping(value = "/errors/empty-jwt")
    public void getEmptyJwtException() {
        throw new CustomException(CustomExceptionStatus.EMPTY_JWT);
    }

    @GetMapping(value = "/errors/access-denied")
    public void getAccessDeniedException() { throw new CustomException(CustomExceptionStatus.ACCOUNT_ACCESS_DENIED);}


}
