package com.github.ismail2ov.ecommercecrossselling.integration;

import com.github.ismail2ov.ecommercecrossselling.domain.Basket;
import com.github.ismail2ov.ecommercecrossselling.domain.BasketRepository;
import com.github.ismail2ov.ecommercecrossselling.domain.Items;
import com.github.ismail2ov.ecommercecrossselling.domain.Product;
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

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasketFeature {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14")
            .withUsername("james")
            .withPassword("bond")
            .withDatabaseName("ecommerce");

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    public TestRestTemplate testRestTemplate;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Test
    public void when_add_product_to_basket_then_items_increase() {

        Product product = new Product(3L, "Logitech Wireless Mouse M185", "10,78 €");
        Basket basket = new Basket(1L, 1L, new Items(List.of(product)));
        basketRepository.save(basket);

        ResponseEntity<Basket> result = testRestTemplate.postForEntity("/api/users/2/basket", product, Basket.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result).isNotNull();
        assertThat(Objects.requireNonNull(result.getBody()).getItems().getProducts()).hasSize(1);
    }

    @Test
    public void when_user_has_not_basket_then_returns_not_found() {

        ResponseEntity<Basket> result = testRestTemplate.getForEntity("/api/users/2/basket", Basket.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
