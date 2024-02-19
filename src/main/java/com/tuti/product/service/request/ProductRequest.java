package com.tuti.product.service.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String name;
    private String content;
    private int price;
    private String discountPolicy;
    private int discountValue;
}
