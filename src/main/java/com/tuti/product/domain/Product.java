package com.tuti.product.domain;

import com.tuti.product.service.request.UpdateProductRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private String content;
    private int price;

    @Enumerated(EnumType.STRING)
    private Discount discount;
    private int discountValue;

    @Builder
    public Product(String name, String content, int price, Discount discount, int discountValue) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.discount = discount;
        this.discountValue = discountValue;
    }

    public void update(UpdateProductRequest updateProductRequest) {
        this.name = updateProductRequest.getName();
        this.content = updateProductRequest.getContent();
        this.discount = Discount.of(updateProductRequest.getDiscountPolicy());
        this.price = updateProductRequest.getPrice();
        this.discountValue = updateProductRequest.getDiscountValue();
    }

}
