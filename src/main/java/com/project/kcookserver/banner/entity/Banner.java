package com.project.kcookserver.banner.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Banner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bannerId;

	private long orders; // 배너 순서

	private boolean used;

	private boolean staticBanner; // 고정 배너

	private String connectedUrl; // 배너를 클릭하면 연결되는 URL

	@Size(max = 500)
	private String webImageUrl;

	@Size(max = 500)
	private String mobileImageUrl;

	public void notUsed() {
		this.used = false;
	}
}
