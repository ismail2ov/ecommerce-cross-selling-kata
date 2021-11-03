package com.github.ismail2ov.ecommercecrossselling.application;

import com.github.ismail2ov.ecommercecrossselling.domain.Product;
import com.github.ismail2ov.ecommercecrossselling.domain.ProductNotFoundException;
import com.github.ismail2ov.ecommercecrossselling.domain.ProductPageDTO;
import com.github.ismail2ov.ecommercecrossselling.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public ProductPageDTO getProductBy(Long id) {
        Product product = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        List<Product> crossSellProducts = productRepository.getCrossSellProducts(id);

        return new ProductPageDTO(product, crossSellProducts);
    }
}
