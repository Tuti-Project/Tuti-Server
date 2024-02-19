package com.tuti.product.controller;

import com.tuti.common.advice.response.ApiResponse;
import com.tuti.product.service.SearchingProductService;
import com.tuti.product.service.response.ProductDetailResponse;
import com.tuti.product.service.response.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchingProductController {
    private final SearchingProductService searchingProductService;

    @Operation(summary = "상품 단건 조회")
    @GetMapping("/product/{productId}")
    public ApiResponse<ProductDetailResponse> getProduct(@RequestParam("productId")Long productId) {
        return ApiResponse.ok(searchingProductService.getProduct(productId));
    }

    @Operation(summary = "전 상품 조회")
    @GetMapping("/products")
    public ApiResponse<List<ProductResponse>> getAllProducts() {
        return ApiResponse.ok(searchingProductService.getAllProducts());
    }

}
