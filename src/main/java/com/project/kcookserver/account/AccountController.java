package com.project.kcookserver.account;

import com.project.kcookserver.account.dto.AccountAuthDto;
import com.project.kcookserver.account.dto.PasswordDto;
import com.project.kcookserver.account.dto.SignInReq;
import com.project.kcookserver.account.dto.SignInRes;
import com.project.kcookserver.account.entity.enumtypes.RoleType;
import com.project.kcookserver.account.sms.EmailDto;
import com.project.kcookserver.account.sms.PhoneNumberDto;
import com.project.kcookserver.account.sms.SmsAuthService;
import com.project.kcookserver.configure.aop.annotation.AccountLog;
import com.project.kcookserver.configure.response.CommonResponse;
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
    private final SmsAuthService smsAuthService;

    @Operation(summary = "회원 가입", description = "회원 가입 DTO로 회원 가입 신청")
    @PostMapping(value = "/sign-up")
    public DataResponse<AccountAuthDto> signUp(@RequestBody @Valid AccountAuthDto dto, Errors errors){
        if (errors.hasErrors()) ValidationExceptionProvider.throwValidError(errors);
        return responseService.getDataResponse(accountService.signUp(dto));
    }

    @Operation(summary = "로그인", description = "로그인 DTO로 JWT 토큰과 Account Id 리턴")
    @PostMapping(value = "/sign-in")
    public DataResponse<SignInRes> signIn(@RequestBody @Valid SignInReq req, Errors errors) {
        if (errors.hasErrors()) ValidationExceptionProvider.throwValidError(errors);
        return responseService.getDataResponse(accountService.signIn(req));
    }

    @AccountLog
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 토큰", dataTypeClass = String.class, paramType = "header")
    })
    @Operation(summary = "로그인한 회원 정보 조회", description = "JWT 토큰으로 인증 된 회원 정보 리턴")
    @GetMapping(value = "/accounts/auth")
    public DataResponse<AccountAuthDto> getAuthAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return responseService.getDataResponse(accountService.getAuthAccount(customUserDetails));
    }

    @AccountLog
    @Operation(summary = "사용자 SMS 인증 토큰 생성", description = "인자로 보내는 전화번호로 SMS Token 전송")
    @PatchMapping(value = "/accounts/sms-token")
    public DataResponse<Integer> updateAccountSmsToken(@RequestBody PhoneNumberDto phoneNumberDto) {
        phoneNumberDto.setPhoneNumber(phoneNumberDto.getPhoneNumber().replaceAll("-",""));
        Integer token = smsAuthService.updateAccountSmsToken(phoneNumberDto.getPhoneNumber());
        return responseService.getDataResponse(token);
    }

    @AccountLog
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 토큰", dataTypeClass = String.class, paramType = "header")
    })
    @Operation(summary = "회원 권한 변경", description = "관리자 권한으로 회원의 Role 변경 API")
    @PatchMapping(value = "/accounts/role")
    public CommonResponse updateAccountRoleByAccountsEmail(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                              @RequestParam(value = "email") String email,
                                                              @RequestParam(value = "role") String role){
        RoleType roleType;
        try {
            roleType = RoleType.valueOf("ROLE_" + role);
        }
        catch (IllegalArgumentException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            throw new CustomException(CustomExceptionStatus.ACCOUNT_NOT_VALID_ROLE);
        }
        accountService.updateAccountRoleByAccountsEmail(email, roleType);
        return responseService.getSuccessResponse();
    }

    @AccountLog
    @Operation(summary = "회원 이메일로 SMS 인증", description = "회원의 이메일을 받아서 SMS 인증 번호 Return")
    @PatchMapping(value = "/accounts/email/sms-token")
    public DataResponse<Integer> getAccountSmsTokenByEmail(@RequestBody EmailDto emailDto) {
        return responseService.getDataResponse(smsAuthService.getAccountSmsTokenByEmail(emailDto.getEmail()));
    }

    @AccountLog
    @Operation(summary = "회원 이메일로 비밀번호 변경", description = "회원의 이메일을 받아서 비밀번호 변경")
    @PatchMapping(value = "/accounts/email/password")
    public CommonResponse updateAccountPasswordByEmail(@RequestBody PasswordDto passwordDto) {
        accountService.updateAccountPasswordByEmail(passwordDto);
        return responseService.getSuccessResponse();
    }

    @AccountLog
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 토큰", dataTypeClass = String.class, paramType = "header")
    })
    @Operation(summary = "회원 탈퇴", description = "회원의 비밀번호를 받아서 회원 탈퇴")
    @PatchMapping(value = "/accounts/auth")
    public CommonResponse deleteAccountByAccountPassword(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody PasswordDto.OnlyPasswordDto passwordDto) {
        accountService.deleteAccountByAccountPassword(customUserDetails, passwordDto);
        return responseService.getSuccessResponse();
    }
}
