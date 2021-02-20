package com.arani.myretailapp.datamanager;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.springdata22.repository.config.EnableIgniteRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.arani.myretailapp.datamanager.entity.ProductPrice;

@Configuration
@EnableIgniteRepositories
public class IgniteCacheConfiguration
{
    /**
     * Creating Apache Ignite instance bean. A bean will be passed
     * to IgniteRepositoryFactoryBean to initialize all Ignite based Spring Data repositories and connect to a cluster.
     */
    @Bean
    public Ignite igniteInstance()
    {
        IgniteConfiguration cfg = new IgniteConfiguration();
        // Setting some custom name for the node.
        cfg.setIgniteInstanceName("springDataNode");
        // Enabling peer-class loading feature.
        cfg.setPeerClassLoadingEnabled(true);
        // Defining and creating a new cache to be used by Ignite Spring Data
        // repository.
        CacheConfiguration ccfg = new CacheConfiguration("ProductPriceCache");
        // Setting SQL schema for the cache.
        ccfg.setIndexedTypes(Long.class, ProductPrice.class);
        cfg.setCacheConfiguration(ccfg);
        return Ignition.start(cfg);
    }
}
