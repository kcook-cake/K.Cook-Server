package com.project.kcookserver.banner.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RegisterBanner {

	private long orders; // 배너 순서

	private String connectedUrl; // 배너를 클릭하면 연결되는 URL

	private MultipartFile webImage;

	private MultipartFile mobileImage;
}
