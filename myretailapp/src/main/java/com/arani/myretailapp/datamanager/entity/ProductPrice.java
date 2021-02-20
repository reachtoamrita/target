package com.arani.myretailapp.datamanager.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import com.arani.myretailapp.dto.Price;
import lombok.Data;
import lombok.NonNull;

@Data
public class ProductPrice
{
    @QuerySqlField(index = true)
    private final Long  productId;

    @NonNull
    private final Price price;
}
