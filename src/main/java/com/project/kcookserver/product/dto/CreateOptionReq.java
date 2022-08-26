package com.project.kcookserver.product.dto;

import com.project.kcookserver.product.entity.enums.OptionsCategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CreateOptionReq {

    @NotNull
    private OptionsCategoryType category;

    private String contents;

    private Integer additionalCost;

    private String title;

    @Size(max = 500)
    private String imageUrl;

    private MultipartFile multipartFile;
}
