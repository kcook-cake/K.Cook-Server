package com.project.kcookserver.banner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterStaticBannerReq {

	private String connectedUrl; // 배너를 클릭하면 연결되는 URL

	private MultipartFile webImage;

	private MultipartFile mobileImage;
}
