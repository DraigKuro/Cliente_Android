package com.example.myapplication.data.network

import com.example.myapplication.data.model.OrderRequest
import com.example.myapplication.data.model.OrderResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderApiClient {
    @POST("orders")
    suspend fun createOrder(@Body request: OrderRequest): Response<OrderResponse>
}