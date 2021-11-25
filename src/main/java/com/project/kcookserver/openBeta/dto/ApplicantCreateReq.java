package com.project.kcookserver.openBeta.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicantCreateReq {

    @NotBlank
    @Pattern(regexp = "^[0-9-]{3,20}$")
    private String phoneNumber;

    private Integer cityIndex;

    private Integer locationIndex;

}
