package com.example.myapplication.data.repository

import com.example.myapplication.data.model.OrderItemRequest
import com.example.myapplication.data.model.OrderRequest
import com.example.myapplication.data.model.OrderResponse
import com.example.myapplication.data.network.OrderApiClient
import jakarta.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private val api: OrderApiClient
) {
    suspend fun createOrder(mesa_uid: String?, items: List<OrderItemRequest>): OrderResponse? {
        return try {
            val response = api.createOrder(OrderRequest(mesa_uid, items))
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}