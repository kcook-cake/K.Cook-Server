package com.project.kcookserver.account.entity;

import com.project.kcookserver.coupon.Coupon;
import com.project.kcookserver.account.dto.AccountAuthDto;
import com.project.kcookserver.account.entity.enumtypes.OAuthType;
import com.project.kcookserver.account.entity.enumtypes.RoleType;
import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.store.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.project.kcookserver.configure.entity.Status.*;
import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String nickname;

    private String email;

    private LocalDate dateOfBirth;

    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private String phoneNumber;

    private String profileImage;

    private String address;

    @Enumerated(EnumType.STRING)
    private OAuthType oAuth;

    private Long kakaoId;

    private Integer smsAuthToken;

    private Integer savings;

    @OneToOne(fetch = LAZY, mappedBy = "account", cascade = ALL)
    private Store store;


    @BatchSize(size = 100)
    @OneToMany(mappedBy = "account", cascade = ALL)
    private List<Coupon> coupons = new ArrayList<>();

    public static Account createAccount(AccountAuthDto dto) {

        return Account.builder()
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .dateOfBirth(dto.getDateOfBirth())
                .status(VALID)
                .role(RoleType.ROLE_USER)
                .savings(0)
                .build();
    }

    public void changeRole(RoleType roleType) {
        this.role = roleType;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDELETED(){
        this.status = DELETED;
    }
}
