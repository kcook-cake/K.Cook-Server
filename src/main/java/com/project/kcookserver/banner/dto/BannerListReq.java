package com.project.kcookserver.banner.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
public class BannerListReq {

	private long orders; // 배너 순서

	private String connectedUrl; // 배너를 클릭하면 연결되는 URL

	private MultipartFile webImage;

	private MultipartFile mobileImage;
}
