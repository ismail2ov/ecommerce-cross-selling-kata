package com.github.ismail2ov.ecommercecrossselling.infrastructure;

import com.github.ismail2ov.ecommercecrossselling.application.BasketService;
import com.github.ismail2ov.ecommercecrossselling.domain.Basket;
import com.github.ismail2ov.ecommercecrossselling.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BasketController {

    private final BasketService basketService;

    @GetMapping("/users/{userId}/basket")
    public Basket getByUserId(@PathVariable("userId") Long userId) {
        return basketService.getBy(userId);
    }

    @PostMapping("/users/{userId}/basket")
    public Basket addProductToBasket(@PathVariable("userId") Long userId, @RequestBody Product product) {
        return basketService.addProductToBasket(userId, product);
    }
}
