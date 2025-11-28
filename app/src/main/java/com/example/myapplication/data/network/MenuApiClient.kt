package com.example.myapplication.data.network

import com.example.myapplication.data.model.MenuModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MenuApiClient {
    @GET("/menus")
    suspend fun getAll(): Response<List<MenuModel>>

    @GET("/menus/{id}")
    suspend fun getById(@Path("id") id: String): Response<MenuModel>
}