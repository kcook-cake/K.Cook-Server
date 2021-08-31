package com.project.kcookserver.orders;

import com.project.kcookserver.configure.response.DataResponse;
import com.project.kcookserver.configure.response.ResponseService;
import com.project.kcookserver.configure.security.authentication.CustomUserDetails;
import com.project.kcookserver.orders.dto.CreateOrdersReq;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Orders API"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/app")
public class OrdersController {

    private final OrdersService ordersService;
    private final ResponseService responseService;

    @Operation(summary = "주문 생성 API", description = "Account가 인증된 상태에서 CreateOrdersReq 객체의 인자로 주문 생성")
    @PostMapping("/orders")
    public DataResponse<Long> createOrders(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CreateOrdersReq createOrdersReq) {
        Long ordersId = ordersService.createOrders(customUserDetails, createOrdersReq);
        return responseService.getDataResponse(ordersId);
    }

}
