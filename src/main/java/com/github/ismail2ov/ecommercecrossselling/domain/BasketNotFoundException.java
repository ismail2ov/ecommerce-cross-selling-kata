package com.github.ismail2ov.ecommercecrossselling.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BasketNotFoundException extends RuntimeException {
}
