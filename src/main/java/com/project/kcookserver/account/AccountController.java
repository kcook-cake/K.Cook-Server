package com.project.kcookserver.account;

import com.project.kcookserver.account.dto.AccountAuthDto;
import com.project.kcookserver.account.dto.SignInReq;
import com.project.kcookserver.account.dto.SignInRes;
import com.project.kcookserver.configure.response.DataResponse;
import com.project.kcookserver.configure.response.ResponseService;
import com.project.kcookserver.configure.response.exception.CustomException;
import com.project.kcookserver.configure.response.exception.CustomExceptionStatus;
import com.project.kcookserver.configure.security.authentication.CustomUserDetails;
import com.project.kcookserver.util.ValidationExceptionProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "로그인", description = "로그인 DTO로 JWT 토큰과 Account Id 리턴")
    @PostMapping(value = "/sign-in")
    public DataResponse<SignInRes> signIn(@RequestBody @Valid SignInReq req, Errors errors) {
        if (errors.hasErrors()) ValidationExceptionProvider.throwValidError(errors);
        return responseService.getDataResponse(accountService.signIn(req));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 토큰", dataType = "String", paramType = "header")
    })
    @Operation(summary = "로그인한 회원 정보 조회", description = "JWT 토큰으로 인증 된 회원 정보 리턴")
    @GetMapping(value = "/accounts/auth")
    public DataResponse<AccountAuthDto> getAuthAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return responseService.getDataResponse(accountService.getAuthAccount(customUserDetails));
    }


}
