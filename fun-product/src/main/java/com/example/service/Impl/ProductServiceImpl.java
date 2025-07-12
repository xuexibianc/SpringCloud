package com.example.service.Impl;

import com.example.entity.Product;
import com.example.mapper.ProductMapper;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product getProductById(Integer productId) {
        return productMapper.getProductById(productId);
    }
}
