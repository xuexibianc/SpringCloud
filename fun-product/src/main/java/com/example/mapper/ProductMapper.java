package com.example.mapper;


import com.example.dto.ProductRequest;
import com.example.entity.Product;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ProductMapper {


    int insert(ProductRequest row);

    int insertSelective(Product row);

    Product selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(Product row);

    int updateByPrimaryKey(Product row);


    Product getProductById(Integer productId);

    int createProduct(ProductRequest productRequest);

    void updateProduct(ProductRequest productRequest);

    int deleteByPrimaryKey(Integer productId);
}