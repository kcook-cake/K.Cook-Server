package com.project.kcookserver.store;

import com.project.kcookserver.configure.aop.annotation.AccountLog;
import com.project.kcookserver.configure.response.CommonResponse;
import com.project.kcookserver.configure.response.DataResponse;
import com.project.kcookserver.configure.response.ResponseService;
import com.project.kcookserver.configure.security.authentication.CustomUserDetails;
import com.project.kcookserver.store.dto.CreateStoreReq;
import com.project.kcookserver.store.dto.StoreDetailRes;
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

@Api(tags = {"Store API"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/app")
public class StoreController {

    private final StoreService storeService;
    private final ResponseService responseService;

    @AccountLog
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 토큰", dataTypeClass = String.class, paramType = "header")
    })
    @Operation(summary = "스토어 조회 API", description = "인증된 Account를 기준으로 스토어 조회")
    @GetMapping("/stores/account/auth")
    public DataResponse<StoreDetailRes> getStoreInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        StoreDetailRes storeDetailRes = storeService.getStoreInfo(customUserDetails);
        return responseService.getDataResponse(storeDetailRes);
    }

    @AccountLog
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 토큰", dataTypeClass = String.class, paramType = "header")
    })
    @Operation(summary = "스토어 생성 API", description = "인증된 Account를 기준으로 스토어 생성")
    @PostMapping("/stores/accounts/auth")
    public DataResponse<Long> createStoreByAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                   @RequestBody @Valid CreateStoreReq dto, Errors errors) {
        if (errors.hasErrors()) ValidationExceptionProvider.throwValidError(errors);
        Long storeId = storeService.createStoreByAccount(customUserDetails,dto);
        return responseService.getDataResponse(storeId);
    }

    @AccountLog
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 토큰", dataTypeClass = String.class, paramType = "header")
    })
    @Operation(summary = "스토어 수정 API", description = "인증된 Account를 기준으로 스토어 수정")
    @PutMapping("/stores/accounts/auth")
    public CommonResponse updateStoreByAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                               @RequestBody @Valid CreateStoreReq dto, Errors errors) {
        if (errors.hasErrors()) ValidationExceptionProvider.throwValidError(errors);
        storeService.updateStoreByAccount(customUserDetails,dto);
        return responseService.getSuccessResponse();
    }

}
