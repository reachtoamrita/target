package com.arani.myretailapp.datamanager.repository;

import org.apache.ignite.springdata22.repository.IgniteRepository;
import org.apache.ignite.springdata22.repository.config.RepositoryConfig;

import com.arani.myretailapp.datamanager.entity.ProductPrice;

@RepositoryConfig(cacheName = "ProductPriceCache")
public interface ProductPriceRepository extends IgniteRepository<ProductPrice, Long>
{
    ProductPrice findByProductId(Long product);
}
