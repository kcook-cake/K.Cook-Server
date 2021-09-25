package com.project.kcookserver.coupon.entity;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.coupon.Coupon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetCouponReq {

    private Long couponId;

    private Status status;

    private Integer discountPrice;

    private String contents;

    private Integer validity;

    private String couponCode;

    private LocalDateTime expirationAt;

    public GetCouponReq(Coupon coupon) {
        this.couponId = coupon.getCouponId();
        this.status = coupon.getStatus();
        this.discountPrice = coupon.getDiscountPrice();
        this.contents = coupon.getContents();
        this.validity = coupon.getValidity();
        this.couponCode = coupon.getCouponCode();
        this.expirationAt = coupon.getExpirationAt();
    }

}
