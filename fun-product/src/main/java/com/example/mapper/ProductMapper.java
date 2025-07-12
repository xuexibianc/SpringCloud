package com.example.mapper;


import com.example.entity.Product;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ProductMapper {
    int deleteByPrimaryKey(Integer productId);

    int insert(Product row);

    int insertSelective(Product row);

    Product selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(Product row);

    int updateByPrimaryKey(Product row);


    Product getProductById(Integer productId);
}