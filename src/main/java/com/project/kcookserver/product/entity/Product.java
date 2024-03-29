package com.project.kcookserver.product.entity;

import static com.project.kcookserver.configure.entity.Status.VALID;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.product.dto.CreateProductReq;
import com.project.kcookserver.review.entity.Review;
import com.project.kcookserver.store.entity.Store;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	@Enumerated(EnumType.STRING)
	private Status status;

	private String name;

	@Setter
	@Size(max = 500)
	private String image;

	@Size(max = 500)
	private String productImage1;

	@Size(max = 500)
	private String productImage2;

	@Size(max = 500)
	private String productImage3;

	@Size(max = 500)
	private String productImage4;

	@Size(max = 500)
	private String productImage5;

	@Size(max = 500)
	private String optionImage1;

	@Size(max = 500)
	private String optionImage2;

	@Size(max = 500)
	private String optionImage3;

	private Integer price;

	private Integer salePrice;

	private Float raiting = 0F;

	private Integer popularityRank;

	private Integer defaultPageCakeSequence;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "storeId")
	private Store store;

	private Long reviewCount;

	private Boolean isCake;

	private Integer salesRate;

	@OneToMany(mappedBy = "product", cascade = ALL)
	private List<Review> reviews = new ArrayList<>();

	private Boolean isTodayCake;

	private Integer todayCakeLimit;

	private Integer todaySaleNumber;

	private Boolean isOriginShow;

	private Boolean isTodayShow;

	public Product(CreateProductReq createProductReq, Account account) {
		this.status = VALID;
		this.name = createProductReq.getName();
		this.price = createProductReq.getPrice();
		this.salePrice = createProductReq.getSalePrice();
		this.store = account.getStore();
		this.isCake = createProductReq.getIsCake();
		this.reviewCount = 0L;
		this.isTodayCake = createProductReq.getIsTodayCake();
		this.todayCakeLimit = createProductReq.getTodayCakeLimit();
		this.todaySaleNumber = createProductReq.getTodaySaleNumber();
		this.isOriginShow = createProductReq.getIsOriginShow();
		this.isTodayShow = createProductReq.getIsTodayShow();
	}


	public void changeDefaultPageSequence(int sequence) {
		this.defaultPageCakeSequence = sequence;
	}

	public void changePopularityRank(int popularityRank) {
		this.popularityRank = popularityRank;
	}

	public void deletePopularityRank() {
		this.popularityRank = null;
	}

	public void plusReviewCount() {
		reviewCount++;
	}

	public void setProductImages(List<String> productImages) {
		if (!productImages.get(0).isEmpty()) {
			this.productImage1 = productImages.get(0);
		}
		if (!productImages.get(1).isEmpty()) {
			this.productImage2 = productImages.get(1);
		}
		if (!productImages.get(2).isEmpty()) {
			this.productImage3 = productImages.get(2);
		}
		if (!productImages.get(3).isEmpty()) {
			this.productImage4 = productImages.get(3);
		}
		if (!productImages.get(4).isEmpty()) {
			this.productImage5 = productImages.get(4);
		}
		if (!productImages.get(5).isEmpty()) {
			this.optionImage1 = productImages.get(5);
		}
		if (!productImages.get(6).isEmpty()) {
			this.optionImage2 = productImages.get(6);
		}
		if (!productImages.get(7).isEmpty()) {
			this.optionImage3 = productImages.get(7);
		}
	}

	public void updateProductTodaySaleCount() {
		this.todaySaleNumber = this.todayCakeLimit;
	}
}
