package com.example.controller;

import com.example.constant.ProductCategory;
import com.example.dto.ProductQueryParams;
import com.example.dto.ProductRequest;
import com.example.entity.Product;
import com.example.service.ProductService;
import com.example.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
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
    public ResponseEntity<Page<Product>> getProducts(
            // 查询条件 Filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,

            // 排序 Sorting
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,

            // 分页 Pagination
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,//取得几笔参数
            @RequestParam(defaultValue = "0") @Min(0) Integer offset//跳过前几笔参数
            ) {

        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        // 取得 product list
       List<Product> productList =  productService.getProducts(productQueryParams);

       // 取得 product 总数
       Integer total = productService.countProduct(productQueryParams);

       // 分页
       Page<Product> page = new Page<>();
       page.setLimit(limit);
       page.setOffset(offset);
       page.setTotal(total);
       page.setResults(productList);
       return ResponseEntity.status(HttpStatus.OK).body(page);

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
