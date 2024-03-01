package com.tuti.product.controller;

import com.tuti.auth.config.AuthenticatedMemberId;
import com.tuti.common.advice.response.ApiResponse;
import com.tuti.product.service.ProductService;
import com.tuti.product.service.request.ProductRequest;
import com.tuti.product.service.request.UpdateProductRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "상품 생성", description = "할인 정책은 정률, 정액법이 있고, 각 정책에 따라 적절한 값을 부여")
    @PostMapping("/new/product")
    public ApiResponse<Void> createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
        return ApiResponse.ok();
    }

    @Operation(summary = "상품 수정")
    @PostMapping("/product/{productId}")
    public ApiResponse<Void> updateProduct(@AuthenticatedMemberId Long memberId, @PathVariable("productId")Long productId,
                                           @RequestBody UpdateProductRequest updateProductRequest) {
        productService.updateProduct(productId, updateProductRequest);
        return ApiResponse.ok();
    }

    @Operation(summary = "상품 삭제")
    @DeleteMapping("/product/{productId}")
    public ApiResponse<Void> deleteProduct(@AuthenticatedMemberId Long memberId, @PathVariable("productId")Long productId) {
        productService.deleteProduct(productId);
        return ApiResponse.ok();
    }

}
