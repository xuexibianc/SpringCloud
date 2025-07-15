package com.example.service.Impl;

import com.example.dto.ProductRequest;
import com.example.entity.Product;
import com.example.mapper.ProductMapper;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product getProductById(Integer productId) {
        return productMapper.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {

        Date now = new Date();
        productRequest.setCreatedDate(now);
        productRequest.setLastModifiedDate(now);

        productMapper.createProduct(productRequest);
        return productRequest.getProductId();
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {

        Date now = new Date();
        productRequest.setLastModifiedDate(now);
        productRequest.setProductId(productId);

        productMapper.updateProduct(productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productMapper.deleteByPrimaryKey(productId);
    }
}
