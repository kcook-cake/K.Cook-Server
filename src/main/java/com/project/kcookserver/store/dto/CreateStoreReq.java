package com.project.kcookserver.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CreateStoreReq {

    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z0-9_-]{3,20}$")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[0-9-]{3,20}$")
    private String contact;

    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s_-]*$")
    private String address;

    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s_-]*$")
    private String area;


}
