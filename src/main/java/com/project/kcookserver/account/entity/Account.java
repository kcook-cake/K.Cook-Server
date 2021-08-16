package com.project.kcookserver.account.entity;

import com.project.kcookserver.account.entity.enumtypes.OAuthType;
import com.project.kcookserver.account.entity.enumtypes.RoleType;
import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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

    private boolean isSmsCertified;

    private Integer Saving;

}
