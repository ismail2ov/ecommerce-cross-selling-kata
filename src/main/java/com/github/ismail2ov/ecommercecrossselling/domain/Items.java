package com.github.ismail2ov.ecommercecrossselling.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Items {
    List<Product> products = new ArrayList<>();
}
