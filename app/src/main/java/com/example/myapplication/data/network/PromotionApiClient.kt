package com.example.myapplication.data.network

import com.example.myapplication.data.model.PromotionModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PromotionApiClient {
    @GET("/promotions")
    suspend fun getAll(): Response<List<PromotionModel>>

    @GET("/promotions/{id}")
    suspend fun getById(@Path("id") id: String): Response<PromotionModel>
}