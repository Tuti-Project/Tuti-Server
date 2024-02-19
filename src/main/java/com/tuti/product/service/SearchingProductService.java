package com.tuti.product.service;

import com.tuti.product.domain.Product;
import com.tuti.product.repository.ProductRepository;
import com.tuti.product.service.exception.ProductNotFoundException;
import com.tuti.product.service.response.ProductDetailResponse;
import com.tuti.product.service.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchingProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    public ProductDetailResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        return new ProductDetailResponse(product);
    }

}
