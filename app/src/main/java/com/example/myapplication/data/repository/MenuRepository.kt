package com.example.myapplication.data.repository

import com.example.myapplication.data.model.MenuModel
import com.example.myapplication.data.network.MenuApiClient
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuRepository @Inject constructor(
    private val api: MenuApiClient,
) {
    private var cachedMenus: List<MenuModel> = emptyList()
    private val mutex = Mutex()

    suspend fun getAllMenus(): List<MenuModel> = mutex.withLock {
        if (cachedMenus.isNotEmpty()) return@withLock cachedMenus

        val response = api.getAll()
        return@withLock if (response.isSuccessful) {
            val drinks = response.body() ?: emptyList()
            cachedMenus = drinks
            drinks
        } else {
            emptyList()
        }
    }

    suspend fun getMenuById(id: String): MenuModel? = mutex.withLock {
        cachedMenus.find { it.id == id }?.let { return@withLock it }

        getAllMenus()
        cachedMenus.find { it.id == id }
    }

    suspend fun refresh() = mutex.withLock {
        cachedMenus = emptyList()
        getAllMenus()
    }
}