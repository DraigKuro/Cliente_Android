package com.example.myapplication.data.network

import com.example.myapplication.data.model.DishModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DishApiClient {
    @GET("/dishes")
    suspend fun getAll(): Response<List<DishModel>>

    @GET("/dishes/{id}")
    suspend fun getById(@Path("id") id: String): Response<DishModel>

    @GET("/dishes/principales")
    suspend fun getPrincipales(): Response<List<DishModel>>

    @GET("/dishes/entrantes")
    suspend fun getEntrantes(): Response<List<DishModel>>

    @GET("/dishes/postres")
    suspend fun getPostres(): Response<List<DishModel>>
}