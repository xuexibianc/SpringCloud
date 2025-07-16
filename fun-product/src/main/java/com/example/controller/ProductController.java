package com.example.controller;

import com.example.constant.ProductCategory;
import com.example.dto.ProductQueryParams;
import com.example.dto.ProductRequest;
import com.example.entity.Product;
import com.example.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    //查詢商品列表的方法
    /**
     * @author wangxinliang@civil.com.cn
     * @return {@link ResponseEntity }<{@link List }<{@link Product }>>
     * @功能介绍  查詢所有商品
     */
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search
            ) {

        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);

       List<Product> productList =  productService.getProducts(productQueryParams);

       return ResponseEntity.status(HttpStatus.OK).body(productList);

    }


    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * @author wangxinliang@civil.com.cn
     * @param productRequest
     * @return {@link ResponseEntity }<{@link Product }>
     * @功能介绍  新增商品
     */
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {

        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }


    /**
     * @author wangxinliang@civil.com.cn
     * @param productId
     * @param productRequest
     * @return {@link ResponseEntity }<{@link Product }>
     * @功能介绍  修改商品
     */
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Integer productId,
            @RequestBody @Valid ProductRequest productRequest) {

        //检查 product 是否存在
        Product product = productService.getProductById(productId);

        if (product == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }

        //修改商品的数据
        productService.updateProduct(productId,productRequest);

        Product updatedProduct = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);

    }

    /**
     * @author wangxinliang@civil.com.cn
     * @param productId
     * @return {@link ResponseEntity }<{@link ? }>
     * @功能介绍  删除商品
     */
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {

        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
