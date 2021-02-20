package com.arani.myretailapp.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ProductNameEndpoint
{
    @GetMapping(value = "/v1/getProductName", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getProductName(@RequestParam(value = "productId") Long productId)
    {
        Map<Long, String> productDetails = new HashMap<>();
        productDetails.put(13860427L, "The Apple product");
        productDetails.put(13860428L, "The Samsung product");
        productDetails.put(13860429L, "The Nokia product");
        if (productDetails.containsKey(productId))
        {
            return productDetails.get(productId);
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "productId does not exist!!!");
        }
    }
}
