package com.tuti.product.service;

import com.tuti.product.domain.Discount;
import com.tuti.product.domain.Product;
import com.tuti.product.repository.ProductRepository;
import com.tuti.product.service.exception.ProductNotFoundException;
import com.tuti.product.service.request.ProductRequest;
import com.tuti.product.service.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        productRepository.save(Product.builder()
                .name(productRequest.getName()).content(productRequest.getContent())
                .price(productRequest.getPrice()).discount(Discount.of(productRequest.getDiscountPolicy()))
                .discountValue(productRequest.getDiscountValue()).build());
    }

    public void updateProduct(Long productId, UpdateProductRequest updateProductRequest) {
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        product.update(updateProductRequest);
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        productRepository.delete(product);
    }

}
