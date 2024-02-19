package com.tuti.product.service.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateProductRequest {
    private String name;
    private String content;
    private int price;
    private int discountValue;
    private String discountPolicy;
}
