package com.github.ismail2ov.ecommercecrossselling.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {

    Optional<Basket> getByUserId(Long userId);

}
