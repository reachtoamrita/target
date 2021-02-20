package com.arani.myretailapp.api;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import com.arani.myretailapp.datamanager.entity.ProductPrice;
import com.arani.myretailapp.datamanager.repository.ProductPriceRepository;
import com.arani.myretailapp.dto.Price;
import com.arani.myretailapp.dto.ProductDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The purpose of this class is to test the {@link RetailRestEndpoint} class
 */
@ContextConfiguration(classes = {RetailRestEndpoint.class})
@WebMvcTest(controllers = RetailRestEndpoint.class, useDefaultFilters = false)
public class RetailRestEndpointsTest
{
    @MockBean
    ProductPriceRepository productPriceRepository;

    @MockBean
    RestTemplate restTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private ProductPrice productPrice;

    private MockMvc mockMvc;

    private ProductDetails productDetails;

    @BeforeEach
    public void init()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        productPrice = new ProductPrice(13860428L, new Price(22.45, "USD"));
        productDetails = new ProductDetails(13860428L, "The Samsung Product",
            new Price(45.23, "USD"));
        doReturn(productPrice).when(productPriceRepository).findByProductId(anyLong());
        doReturn(productPrice).when(productPriceRepository).save(anyLong(), ArgumentMatchers.any(ProductPrice.class));
    }

    @Test
    public void testThatGetProductWorksFineWithExpectedData() throws Exception
    {
        String productId = "13860428";
        String productName = "The Samsung product";

        when(restTemplate.getForObject(anyString(), ArgumentMatchers.any(Class.class))).thenReturn(productName);
        mockMvc.perform(get("/v1/productDetails/" + productId))
            .andExpect(status().isOk());
    }

    @Test
    public void testThatGetProductThrowsExceptionWithUnknownData() throws Exception
    {
        String productId = "13860423";
        when(restTemplate.getForObject(anyString(), ArgumentMatchers.any(Class.class))).thenThrow(
            new ResponseStatusException(HttpStatus.NOT_FOUND, "productId does not exist!!!"));
        mockMvc.perform(get("/v1/productDetails/" + productId))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void testThatUpdateProductWorksFineWithExpectedData() throws Exception
    {
        String productId = "13860428";
        mockMvc.perform(put("/v1/productDetails/" + productId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(productDetails)))
            .andExpect(status().isOk());
    }

    @Test
    public void testThatUpdateProductThrowsExceptionWithUnknownData() throws Exception
    {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String productId = "13860423";
        doReturn(null).when(productPriceRepository).findByProductId(anyLong());
        when(restTemplate.getForObject(anyString(), ArgumentMatchers.any(Class.class))).thenThrow(
            new ResponseStatusException(HttpStatus.NOT_FOUND, "productId does not exist!!!"));
        mockMvc.perform(put("/v1/productDetails/" + productId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(productDetails)))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void testThatAddProductPriceDetailsWorksFineWithExpectedData() throws Exception
    {
        mockMvc.perform(get("/v1/addProductPriceDetails"))
            .andExpect(status().isOk());
    }

    @Test
    public void testThatListProductDetailsWorksFineWithExpectedData() throws Exception
    {
        List<ProductPrice> productPrices = new ArrayList<>();
        productPrices.add(productPrice);
        doReturn(productPrices).when(productPriceRepository).findAll();
        when(restTemplate.getForObject(anyString(), ArgumentMatchers.any(Class.class))).thenReturn("The Samsung Product");
        mockMvc.perform(get("/v1/listProductDetails"))
            .andExpect(status().isOk());
    }
    @Test
    public void testListProductDetailsThrowsExceptionWithUnknownData() throws Exception
    {
        String productId = "13860423";
        List<ProductPrice> productPrices = new ArrayList<>();
        productPrices.add(productPrice);
        doReturn(productPrices).when(productPriceRepository).findAll();
        when(restTemplate.getForObject(anyString(), ArgumentMatchers.any(Class.class))).thenThrow(
            new ResponseStatusException(HttpStatus.NOT_FOUND, "productId does not exist!!!"));
        mockMvc.perform(get("/v1/productDetails/" + productId))
            .andExpect(status().is4xxClientError());
    }

    public static String asJsonString(final Object obj)
    {
        try
        {
            return new ObjectMapper().writeValueAsString(obj);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}