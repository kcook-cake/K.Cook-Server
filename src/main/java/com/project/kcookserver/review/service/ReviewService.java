package com.project.kcookserver.review.service;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.orders.OrdersRepository;
import com.project.kcookserver.orders.entity.Orders;
import com.project.kcookserver.product.entity.Product;
import com.project.kcookserver.product.repository.ProductRepository;
import com.project.kcookserver.review.dto.CreateReviewDto;
import com.project.kcookserver.review.entity.Review;
import com.project.kcookserver.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final OrdersRepository ordersRepository;
	private final ProductRepository productRepository;

	@Transactional(readOnly = false)
	public void createReview(Account account, CreateReviewDto createReviewDto) {
		Orders orders = ordersRepository.findById(createReviewDto.getOrdersId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다"));
		Product product = productRepository.findById(createReviewDto.getProductId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다"));
		product.plusReviewCount();
		Review review = new Review(Status.VALID, orders, account, product, createReviewDto.getContents(), createReviewDto.getRaiting());
		reviewRepository.save(review);
	}
}
