package com.project.kcookserver.coupon;

import com.project.kcookserver.configure.response.DataResponse;
import com.project.kcookserver.configure.response.ResponseService;
import com.project.kcookserver.coupon.entity.GetCouponReq;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"Coupon API"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/app")
public class CouponController {

    private final CouponService couponService;
    private final ResponseService responseService;

    @Operation(summary = "계정이 소유한 쿠폰 조회", description = "accountId가 필요")
    @GetMapping(value = "/accounts/{accountId}/coupons")
    DataResponse<List<GetCouponReq>> getCouponsByAccountId(
            @PathVariable(name = "accountId") Long accountId) {
        List<GetCouponReq> list = couponService.getCouponsByAccountId(accountId);
        return responseService.getDataResponse(list);
    }

}
