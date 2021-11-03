package com.github.ismail2ov.ecommercecrossselling.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;

import java.util.List;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductPageDTO {

    Product product;

    List<Product> crossSelling;
}
