package com.project.kcookserver.review.repository;

import com.project.kcookserver.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository  extends JpaRepository<Review, Long> {
}
