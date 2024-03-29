package com.project.kcookserver.banner;

import com.project.kcookserver.banner.dto.BannerListReq;
import com.project.kcookserver.banner.dto.RegisterCarouselBannerReq;
import com.project.kcookserver.banner.dto.RegisterStaticBannerReq;
import com.project.kcookserver.banner.dto.registeredBanner;
import com.project.kcookserver.banner.entity.Banner;
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

	public registeredBanner getStaticBanner() {
		Banner banner = bannerRepository.findByUsedIsTrueAndStaticBannerIsTrue();
		return new registeredBanner(banner);
	}

	public List<registeredBanner> getCarouselBanners() {
		List<Banner> banners = bannerRepository.findByUsedIsTrueAndStaticBannerIsFalse();
		return banners.stream().map(registeredBanner::new).collect(Collectors.toList());
	}

	@Transactional
	public void registerCarouselBanners(RegisterCarouselBannerReq registerCarouselBannerReqs) throws Exception {

		List<Long> ids = bannerRepository.findByUsedIsTrueAndStaticBannerIsFalse().stream().map(Banner::getBannerId).collect(Collectors.toList());
		bannerRepository.updateBannerNotUsedIdIn(ids);

		List<Banner> banners = new ArrayList<>();
		for (BannerListReq bannerReq : registerCarouselBannerReqs.getBannerListReq()) {
			String webBannerUrl = uploadBannerImage(bannerReq.getWebImage());
			String mobileBannerUrl = uploadBannerImage(bannerReq.getMobileImage());

			Banner banner = Banner.builder()
				.orders(bannerReq.getOrders())
				.used(true)
				.staticBanner(false)
				.connectedUrl(bannerReq.getConnectedUrl())
				.webImageUrl(webBannerUrl)
				.mobileImageUrl(mobileBannerUrl).build();

			banners.add(banner);
		}

		bannerRepository.saveAll(banners);
	}

	@Transactional
	public void registerStaticBanner(RegisterStaticBannerReq registerStaticBannerReq) throws IOException {
		String webBannerUrl = uploadBannerImage(registerStaticBannerReq.getWebImage());
		String mobileBannerUrl = uploadBannerImage(registerStaticBannerReq.getMobileImage());

		Banner beforeBanner = bannerRepository.findByUsedIsTrueAndStaticBannerIsTrue();
		beforeBanner.notUsed();

		Banner banner = Banner.builder()
			.orders(1)
			.used(true)
			.staticBanner(true)
			.connectedUrl(registerStaticBannerReq.getConnectedUrl())
			.webImageUrl(webBannerUrl)
			.mobileImageUrl(mobileBannerUrl).build();

		bannerRepository.save(banner);
	}

	private String uploadBannerImage(MultipartFile file) throws IOException {
		return s3Uploader.upload(file, "banner");
	}
}
