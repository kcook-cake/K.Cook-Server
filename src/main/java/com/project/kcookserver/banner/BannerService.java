package com.project.kcookserver.banner;

import com.project.kcookserver.banner.dto.RegisterCarouselBannerReq;
import com.project.kcookserver.banner.entity.Banner;
import com.project.kcookserver.banner.vo.RegisterBanner;
import com.project.kcookserver.configure.s3.S3Uploader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BannerService {

	private final BannerRepository bannerRepository;
	private final S3Uploader s3Uploader;

	@Transactional
	public void registerBanners(RegisterCarouselBannerReq registerCarouselBannerReqs) throws Exception {

		List<Long> ids = bannerRepository.findByUsedIsTrue().stream().map(Banner::getBannerId).collect(Collectors.toList());
		bannerRepository.updateBannerNotUsedIdIn(ids);

		List<Banner> banners = new ArrayList<>();
		for (RegisterBanner registerBanner : registerCarouselBannerReqs.getRegisterBanners()) {
			String webImageUrl = uploadBannerImage(registerBanner.getWebImage());
			String mobileImageUrl = uploadBannerImage(registerBanner.getMobileImage());

			Banner banner = Banner.builder()
				.orders(registerBanner.getOrders())
				.used(true)
				.staticBanner(false)
				.connectedUrl(registerBanner.getConnectedUrl())
				.webImageUrl(webImageUrl)
				.mobileImageUrl(mobileImageUrl).build();

			banners.add(banner);
		}

		bannerRepository.saveAll(banners);
	}

	private String uploadBannerImage(MultipartFile file) throws IOException {
		return s3Uploader.upload(file, "banner");
	}
}
