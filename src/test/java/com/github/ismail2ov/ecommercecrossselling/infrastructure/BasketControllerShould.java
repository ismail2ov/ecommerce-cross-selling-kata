package com.github.ismail2ov.ecommercecrossselling.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ismail2ov.ecommercecrossselling.application.BasketService;
import com.github.ismail2ov.ecommercecrossselling.domain.Basket;
import com.github.ismail2ov.ecommercecrossselling.domain.Items;
import com.github.ismail2ov.ecommercecrossselling.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BasketController.class)
class BasketControllerShould {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BasketService basketService;

    @Test
    void return_basket_without_items() throws Exception {

        Basket basket = new Basket(1L, 1L, new Items());

        when(basketService.getBy(1L)).thenReturn(basket);

        this.mockMvc
                .perform(get("/api/users/1/basket"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.items.products.size()").value(0));
    }

    @Test
    void add_product_to_basket() throws Exception {
        Product product = new Product(3L, "Logitech Wireless Mouse M185", "10,78 €");
        Basket basket = new Basket(1L, 1L, new Items(List.of(product)));

        when(basketService.addProductToBasket(1L, product)).thenReturn(basket);

        this.mockMvc
                .perform(post("/api/users/1/basket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(product)))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.items.size()").value(1));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}