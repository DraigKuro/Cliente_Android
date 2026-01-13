package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("mesa_uid") val mesaUid: String?,
    @SerializedName("items") val items: List<OrderItemRequest>
)

data class OrderItemRequest(
    val _id: String,
    val cantidad: Int
)

data class OrderResponse(
    val success: Boolean,
    val message: String,
    val order: OrderData?
)

data class OrderData(
    val _id: String,
    val tableId: String,
    val total: Double,
    val status: String
)