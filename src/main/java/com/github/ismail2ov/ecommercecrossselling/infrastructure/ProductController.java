package com.github.ismail2ov.ecommercecrossselling.infrastructure;

import com.github.ismail2ov.ecommercecrossselling.application.ProductService;
import com.github.ismail2ov.ecommercecrossselling.domain.Product;
import com.github.ismail2ov.ecommercecrossselling.domain.ProductPageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<Product> getAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductPageDTO getById(@PathVariable("id") Long id) {
        return productService.getProductBy(id);
    }
}
