package com.tuti.product.domain;

import com.tuti.product.domain.exception.InvalidDiscountPolicyException;

import java.util.Arrays;

public enum Discount {
    FIXED_RATE("정률") {
        public int getDiscountedPrice(int price, int rate) { return (int) (price - (rate * 0.01 * price));}
    },
    FIXED_AMOUNT("정액"){
        public int getDiscountedPrice(int price, int amount) { return price - amount;}
    },
    NONE("없음") {
        public int getDiscountedPrice(int price, int value) { return price;}
    };

    private String discountPolicy;

    Discount(String discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public abstract int getDiscountedPrice(int price, int value);

    public String getDiscountPolicy() {
        return discountPolicy;
    }

    public static Discount of(String discountPolicy) {
        return Arrays.stream(Discount.values())
                .filter(discount -> discount.getDiscountPolicy().equals(discountPolicy))
                .findFirst()
                .orElseThrow(() -> new InvalidDiscountPolicyException());
    }
}
