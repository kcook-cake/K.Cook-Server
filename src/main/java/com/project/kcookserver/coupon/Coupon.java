package com.project.kcookserver.coupon;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.project.kcookserver.configure.entity.Status.*;
import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Coupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    private Integer discountPrice;

    private String contents;

    private Integer validity;

    private String couponCode;

    private LocalDateTime expirationAt;

    public Coupon(Account account, Integer discountPrice, String contents, Integer validity) {
        this.status = VALID;
        this.account = account;
        this.discountPrice = discountPrice;
        this.contents = contents;
        this.validity = validity;
        this.couponCode = UUID.randomUUID().toString().substring(8);
        this.expirationAt = LocalDateTime.now().plusDays(validity);
    }

}
