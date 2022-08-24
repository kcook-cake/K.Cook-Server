package com.project.kcookserver.banner.dto;

import com.project.kcookserver.banner.vo.RegisterBanner;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterCarouselBannerReq {

	List<RegisterBanner> registerBanners;
}
