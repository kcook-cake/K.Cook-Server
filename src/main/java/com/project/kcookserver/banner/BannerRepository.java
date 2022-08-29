package com.project.kcookserver.banner;

import com.project.kcookserver.banner.entity.Banner;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

	List<Banner> findByUsedIsTrueAndStaticBannerIsFalse();

	Banner findByUsedIsTrueAndStaticBannerIsTrue();

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Banner b set b.used = false where b.bannerId in :ids")
	void updateBannerNotUsedIdIn(List<Long> ids);
}

