package com.arani.myretailapp.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {ProductNameEndpoint.class})
@WebMvcTest(controllers = ProductNameEndpoint.class, useDefaultFilters = false)
class ProductNameEndpointTest
{

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testThatGetProductNameWorksFineWithExpectedData() throws Exception
    {
        mockMvc.perform(get("/v1/getProductName")
            .queryParam("productId", "13860427"))
            .andExpect(status().isOk());
    }

    @Test
    public void testThatGetProductNameThrowsExcptionWithUnexpectedData() throws Exception
    {
        mockMvc.perform(get("/v1/getProductName")
            .queryParam("productId", "13860425"))
            .andExpect(status().is4xxClientError());
    }
}