package com.arani.myretailapp.api;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.arani.myretailapp.datamanager.entity.ProductPrice;
import com.arani.myretailapp.datamanager.repository.ProductPriceRepository;
import com.arani.myretailapp.dto.Price;
import com.arani.myretailapp.dto.ProductDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RetailRestEndpoint
{
    @NonNull
    private ProductPriceRepository productPriceRepository;

    @NonNull
    private RestTemplate restTemplate ;

    @GetMapping(value = "/v1/productDetails/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDetails getProductDetails(@PathVariable(value = "productId") Long productId)
    {
        String productName = getProductName(productId);
        ProductPrice productPrice = productPriceRepository.findByProductId(productId);
        ProductDetails productDetails = new ProductDetails(productId, productName, productPrice.getPrice());
        return productDetails;
    }

    @PutMapping(value = "/v1/productDetails/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateProductDetails(@PathVariable(value = "productId") Long productId,
                                     @Valid @RequestBody ProductDetails productDetails)
    {
        ProductPrice productPrice = productPriceRepository.findByProductId(productId);
        if (!ObjectUtils.isEmpty(productPrice))
        {
            productPriceRepository.save(productDetails.getId(), new ProductPrice(productId, productDetails.getCurrentPrice()));
        }
        else
        {
            log.error("Product Id is not available in database");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "productId does not exist!!!");
        }
    }

    @GetMapping(value = "/v1/addProductPriceDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<ProductPrice> addProductPriceDetails()
    {
        //TODO: Add Unit testcases for this
        productPriceRepository.deleteAll();
        Map<Long, ProductPrice> productPriceMap = new HashMap<>();
        productPriceMap.put(13860427L, new ProductPrice(13860427L, new Price(21.54, "USD")));
        productPriceMap.put(13860428L, new ProductPrice(13860428L, new Price(22.54, "USD")));
        productPriceMap.put(13860429L, new ProductPrice(13860429L, new Price(23.54, "USD")));
        productPriceRepository.save(productPriceMap);
        return productPriceRepository.findAll();
    }

    @GetMapping(value = "/v1/listProductDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDetails> listProductDetails()
    {
        //TODO: Add Unit testcases for this
        Iterable<ProductPrice> productPrices = productPriceRepository.findAll();
        List<ProductDetails> productDetailsList = new ArrayList<>();
        for (ProductPrice productPrice : productPrices)
        {
            productDetailsList.add(new ProductDetails(productPrice.getProductId(),
                getProductName(productPrice.getProductId()), productPrice.getPrice()));
        }
        return productDetailsList;
    }

    private String getProductName(Long productId)
    {
        //TODO: Fix to take the url from application.properties
        //TODO: When product name app is down //exception handling
        String uri = "http://localhost:8080/v1/getProductName";
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri)
            .queryParam("productId", productId).build();
        return restTemplate.getForObject(builder.toUriString(), String.class);
    }
}
