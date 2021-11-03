package com.github.ismail2ov.ecommercecrossselling.integration;

import com.github.ismail2ov.ecommercecrossselling.domain.Product;
import com.github.ismail2ov.ecommercecrossselling.domain.ProductPageDTO;
import com.github.ismail2ov.ecommercecrossselling.domain.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductFeature {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14")
            .withUsername("james")
            .withPassword("bond")
            .withDatabaseName("ecommerce");

    @Autowired
    ProductRepository productRepository;

    @Autowired
    public TestRestTemplate testRestTemplate;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Test
    public void when_get_products_then_return_all_products() {

        productRepository.save(new Product(1L, "Dell Latitude 3301 Intel Core i7-8565U/8GB/512GB SSD/13.3", "999,00 €"));
        productRepository.save(new Product(2L, "Samsonite Airglow Laptop Sleeve 13.3", "41,34 €"));
        productRepository.save(new Product(3L, "Logitech Wireless Mouse M185", "10,78 €"));
        productRepository.save(new Product(4L, "Fellowes Mouse Pad Black", "1,34 €"));

        ResponseEntity<Product[]> result = testRestTemplate.getForEntity("/api/products", Product[].class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result).isNotNull();
        assertThat(result.getBody()).hasSize(4);
    }

    @Test
    public void when_get_product_by_id_then_return_also_cross_sell_products() {

        Product product1 = new Product(1L, "Dell Latitude 3301 Intel Core i7-8565U/8GB/512GB SSD/13.3", "999,00 €");
        productRepository.save(product1);
        productRepository.save(new Product(2L, "Samsonite Airglow Laptop Sleeve 13.3", "41,34 €"));
        productRepository.save(new Product(3L, "Logitech Wireless Mouse M185", "10,78 €"));

        productRepository.addCrossSellProducts(1L, 2L);
        productRepository.addCrossSellProducts(1L, 3L);

        ResponseEntity<ProductPageDTO> result = testRestTemplate.getForEntity("/api/products/1", ProductPageDTO.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result).isNotNull();
        assertThat(result.getBody().getProduct()).isEqualTo(product1);
        assertThat(result.getBody().getCrossSelling()).hasSize(2);
    }
}
