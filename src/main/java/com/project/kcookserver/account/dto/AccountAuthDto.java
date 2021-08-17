package com.project.kcookserver.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonProperty.Access;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountAuthDto {

    @JsonProperty(access = Access.READ_ONLY)
    private Long accountId;

    @NotBlank
    @Length(min=3, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$")
    private String signInId;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min=3, max = 20)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9_-]{3,20}$")
    private String nickname;

    @NotBlank
    @JsonProperty(access = Access.WRITE_ONLY)
    @Length(min=8, max= 50)
    private String password;

    @NotBlank
    @Pattern(regexp = "^[0-9-]{3,20}$")
    private String phoneNumber;

    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s_-]*$")
    private String address;

    private LocalDate dateOfBirth;

    @JsonProperty(access = Access.READ_ONLY)
    private String jwt;

}
