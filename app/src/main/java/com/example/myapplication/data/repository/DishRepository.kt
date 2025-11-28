package com.example.myapplication.data.repository

import com.example.myapplication.data.model.DishModel
import com.example.myapplication.data.network.DishApiClient
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DishRepository @Inject constructor(
    private val api: DishApiClient,
) {
    private var cachedDishes: List<DishModel> = emptyList()
    private val mutex = Mutex()

    suspend fun getAllDishes(): List<DishModel> = mutex.withLock {
        if (cachedDishes.isNotEmpty()) return@withLock cachedDishes

        val response = api.getAll()
        return@withLock if (response.isSuccessful) {
            val dishes = response.body() ?: emptyList()
            cachedDishes = dishes
            dishes
        } else {
            emptyList()
        }
    }

    suspend fun getDishById(id: String): DishModel? = mutex.withLock {
        cachedDishes.find { it.id == id }?.let { return@withLock it }

        getAllDishes()
        cachedDishes.find { it.id == id }
    }

    suspend fun getPrincipales(): List<DishModel> = getByCategory("principales")
    suspend fun getEntrantes(): List<DishModel> = getByCategory("entrantes")
    suspend fun getPostres(): List<DishModel> = getByCategory("postres")

    private suspend fun getByCategory(endpoint: String): List<DishModel> = mutex.withLock {
        val response = when (endpoint) {
            "principales" -> api.getPrincipales()
            "entrantes" -> api.getEntrantes()
            "postres" -> api.getPostres()
            else -> return@withLock emptyList()
        }

        if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            emptyList()
        }
    }

    suspend fun refresh() = mutex.withLock {
        cachedDishes = emptyList()
        getAllDishes()
    }
}