package com.project.kcookserver.review.entity;

import static javax.persistence.FetchType.LAZY;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.orders.entity.Orders;
import com.project.kcookserver.product.entity.Product;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "ordersId")
    private Orders orders;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "accountId")
    private Account account;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    @Enumerated(EnumType.STRING)
    private ReviewContent contents;

    private Integer raiting;

    public Review(Status status, Orders orders, Account account, Product product, ReviewContent contents, Integer raiting) {
        this.status = status;
        this.orders = orders;
        this.account = account;
        this.product = product;
        this.contents = contents;
        this.raiting = raiting;
    }

    public enum ReviewContent {
        REVIEW1("맛이 좋아요"),
        REVIEW2("디자인이 예뻐요"),
        REVIEW3("선물하기 좋아요"),
        REVIEW4("사장님이 친절해요"),
        REVIEW5("크림이 맛있어요"),
        REVIEW6("디저트가 맛있어요"),
        REVIEW7("가성비가 좋아요"),
        REVIEW8("매장이 예뻐요"),
        REVIEW9("특별한 디자인이 있어요"),
        REVIEW10("주차하기 편해요"),
        REVIEW11("깔끔해요"),
        REVIEW12("레터링이 예뻐요"),
        REVIEW13("이벤트하기 좋아요"),
        REVIEW14("달콤해요"),
        REVIEW15("추억남기기 좋아요"),
        REVIEW16("귀여워요"),
        REVIEW17("접근성이 좋아요"),
        REVIEW18("또 구매하고 싶어요"),
        REVIEW19("위생적이에요"),
        REVIEW20("사진찍기 좋아요");

        private String korean;

        ReviewContent(String korean) {
            this.korean = korean;
        }

        public String getKorean() {
            return korean;
        }
    }
}
