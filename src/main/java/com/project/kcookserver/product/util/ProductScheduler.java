package com.project.kcookserver.product.util;

import static com.project.kcookserver.configure.entity.Status.*;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.product.entity.Product;
import com.project.kcookserver.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Component
public class ProductScheduler {

	private final ProductRepository productRepository;

	@Scheduled(cron = "0 0 0 * * *")
	public void updateProductTodaySaleCount() {
		List<Product> allProductByStatus = productRepository.findAllByIsCakeAndStatus(true, VALID);
		allProductByStatus.forEach(Product::updateProductTodaySaleCount);
	}
}
