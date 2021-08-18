package com.project.kcookserver.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInReq {

    @NotBlank
    @Length(min=3, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$")
    private String signInId;;

    @NotBlank
    @Length(min=8, max= 50)
    private String password;

}
