package com.tuti.product.service.response;

import com.tuti.product.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDetailResponse {
    private Long productId;
    private String name;
    private String content;
    private int price;
    private int discountValue;
    private int discountedPrice;
    private String discountPolicy;

    public ProductDetailResponse(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.discountValue = product.getDiscountValue();
        this.discountedPrice = product.getDiscount().getDiscountedPrice(price, discountValue);
        this.discountPolicy = product.getDiscount().getDiscountPolicy();
        this.content = product.getContent();
    }
}
