# target
This repository contains my retail app code


Below are the APIs that is developed as part of My retail app. I have used Ignite in-memory database.

1. GET http://localhost:8080/v1/addProductPriceDetails
This API is used to add the product id and product price data to database.
 
Sample Output is
   [
    {
        "productId": 13860427,
        "price": {
            "value": 21.54,
            "currencyCode": "USD"
        }
    },
   {
        "productId": 13860429,
        "price": {
            "value": 23.54,
            "currencyCode": "USD"
        }
   },
   {
        "productId": 13860428,
        "price": {
            "value": 22.54,
            "currencyCode": "USD"
        }
   }
   ]
2. This API is to list All the products 
GET http://localhost:8080/v1/listProductDetails
Sample Output is 
   [
       {
           "id": 13860427,
           "name": "The Apple product",
           "currentPrice": {
               "value": 21.54,
               "currencyCode": "USD"
           }
        },
        {
           "id": 13860429,
           "name": "The Nokia product",
           "currentPrice": {
               "value": 48.54,
               "currencyCode": "USD"
            }
        },
       {
           "id": 13860428,
           "name": "The Samsung product",
           "currentPrice": {
               "value": 22.54,
               "currencyCode": "USD"
            }
        }
   ]

3. This API is to get the product Name
GET http://localhost:9090/v1/getProductName?productId=13860429
Sample Output is 
The Nokia product   
   
4. This API is used to get tProductDetails by passing productId
GET http://localhost:8080/v1/productDetails/13860429
   {
       "id": 13860429,
       "name": "The Nokia product",
       "currentPrice": {
           "value": 23.54,
           "currencyCode": "USD"
       }
   }
   
5. This API is used to put the data to database
PUT   http://localhost:8080/v1/productDetails/13860429
   Request Body is -
   {
       "id": 13860429,
       "name": "The Nokia product",
       "currentPrice": {
           "value": 48.54,
           "currencyCode": "USD"
       }
   }
   
Output - Status OK.