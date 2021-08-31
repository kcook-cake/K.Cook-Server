package com.project.kcookserver.orders.dto;

import com.project.kcookserver.orders.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateOrdersReq {

    @NotBlank
    private Long productId;

    private List<Long> ordersList;

    @NotNull
    private PaymentType paymentType;

    @NotNull
    private LocalDateTime pickUpAt;

}
