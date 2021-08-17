package com.project.kcookserver.account;

import com.project.kcookserver.account.dto.AccountAuthDto;
import com.project.kcookserver.configure.response.DataResponse;
import com.project.kcookserver.configure.response.ResponseService;
import com.project.kcookserver.configure.response.exception.CustomException;
import com.project.kcookserver.configure.response.exception.CustomExceptionStatus;
import com.project.kcookserver.util.ValidationExceptionProvider;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = {"Account API"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/app")
public class AccountController {

    private final ResponseService responseService;
    private final AccountService accountService;

    @Operation(summary = "회원 가입", description = "회원 가입 DTO로 회원 가입 신청")
    @PostMapping(value = "/sign-up")
    public DataResponse<AccountAuthDto> signUp(@RequestBody @Valid AccountAuthDto dto, Errors errors){
        if (errors.hasErrors()) ValidationExceptionProvider.throwValidError(errors);
        if (dto.getDateOfBirth() == null)
            throw new CustomException(CustomExceptionStatus.POST_USERS_EMPTY_BIRTH_OF_DATE);
        return responseService.getDataResponse(accountService.signUp(dto));
    }


}
