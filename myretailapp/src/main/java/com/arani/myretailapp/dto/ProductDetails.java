package com.arani.myretailapp.dto;

import lombok.Data;

@Data
public class ProductDetails
{
    private final Long id;

    private final String name;

    private final Price currentPrice;


}
