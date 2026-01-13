package com.example.myapplication.domain.order

import com.example.myapplication.data.model.OrderItemRequest
import com.example.myapplication.data.model.OrderResponse
import com.example.myapplication.data.repository.OrderRepository
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(mesa_uid: String?, items: List<OrderItemRequest>): OrderResponse? {
        return repository.createOrder(mesa_uid, items)
    }
}