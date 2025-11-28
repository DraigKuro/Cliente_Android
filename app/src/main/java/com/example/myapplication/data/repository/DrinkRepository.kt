package com.example.myapplication.data.repository

import com.example.myapplication.data.model.DrinkModel
import com.example.myapplication.data.network.DrinkApiClient
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrinkRepository @Inject constructor(
    private val api: DrinkApiClient,
){
    private var cachedDrinks: List<DrinkModel> = emptyList()
    private val mutex = Mutex()

    suspend fun getAllDrinks(): List<DrinkModel> = mutex.withLock {
        if (cachedDrinks.isNotEmpty()) return@withLock cachedDrinks

        val response = api.getAll()
        return@withLock if (response.isSuccessful) {
            val drinks = response.body() ?: emptyList()
            cachedDrinks = drinks
            drinks
        } else {
            emptyList()
        }
    }

    suspend fun getDrinkById(id: String): DrinkModel? = mutex.withLock {
        cachedDrinks.find { it.id == id }?.let { return@withLock it }

        getAllDrinks()
        cachedDrinks.find { it.id == id }
    }

    suspend fun refresh() = mutex.withLock {
        cachedDrinks = emptyList()
        getAllDrinks()
    }
}