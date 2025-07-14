package com.example.service;

import com.example.dto.ProductRequest;
import com.example.entity.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
