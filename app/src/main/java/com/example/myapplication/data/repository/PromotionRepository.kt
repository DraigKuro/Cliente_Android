package com.example.myapplication.data.repository
import com.example.myapplication.data.model.PromotionModel
import com.example.myapplication.data.network.PromotionApiClient
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PromotionRepository @Inject constructor(
    private val api: PromotionApiClient
){
    private var cachedPromotions: List<PromotionModel> = emptyList()
    private val mutex = Mutex()

    suspend fun getAllPromotions(): List<PromotionModel> = mutex.withLock {
        if (cachedPromotions.isNotEmpty()) return@withLock cachedPromotions

        val response = api.getAll()
        return@withLock if (response.isSuccessful) {
            val drinks = response.body() ?: emptyList()
            cachedPromotions = drinks
            drinks
        } else {
            emptyList()
        }
    }

    suspend fun getPromotionById(id: String): PromotionModel? = mutex.withLock {
        cachedPromotions.find { it.id == id }?.let { return@withLock it }

        getAllPromotions()
        cachedPromotions.find { it.id == id }
    }

    suspend fun refresh() = mutex.withLock {
        cachedPromotions = emptyList()
        getAllPromotions()
    }
}