package com.example.myapplication.data.network

import com.example.myapplication.data.model.DrinkModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DrinkApiClient {
    @GET("/drinks")
    suspend fun getAll(): Response<List<DrinkModel>>

    @GET("/drinks/{id}")
    suspend fun getById(@Path("id") id: String): Response<DrinkModel>

}