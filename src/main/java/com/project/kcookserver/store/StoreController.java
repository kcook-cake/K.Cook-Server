package com.project.kcookserver.store;

import com.project.kcookserver.configure.response.DataResponse;
import com.project.kcookserver.configure.response.ResponseService;
import com.project.kcookserver.configure.security.authentication.CustomUserDetails;
import com.project.kcookserver.store.dto.StoreDetailRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Store API"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/app")
public class StoreController {

    private final StoreService storeService;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 토큰", dataType = "String", paramType = "header")
    })
    @Operation(summary = "스토어 조회 API", description = "인증된 Account를 기준으로 스토어 조회")
    @GetMapping("/stores/account/auth")
    public DataResponse<StoreDetailRes> getStoreInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        StoreDetailRes storeDetailRes = storeService.getStoreInfo(customUserDetails);
        return responseService.getDataResponse(storeDetailRes);
    }

}
