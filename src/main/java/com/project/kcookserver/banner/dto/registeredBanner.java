package com.project.kcookserver.banner.dto;

import com.project.kcookserver.banner.entity.Banner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class registeredBanner {

	private long orders; // 배너 순서

	private String connectedUrl; // 배너를 클릭하면 연결되는 URL

	private String webImageUrl;

	private String mobileImageUrl;

	public registeredBanner(Banner banner) {
		this.orders = banner.getOrders();
		this.connectedUrl = banner.getConnectedUrl();
		this.webImageUrl = banner.getWebImageUrl();
		this.mobileImageUrl = banner.getMobileImageUrl();
	}
}
