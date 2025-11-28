package com.example.myapplication.data.repository

import com.example.myapplication.data.network.TableApiClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TableRepository @Inject constructor(
    private val api: TableApiClient
) {
    suspend fun getTableByUid(uid: String): TableStatus {
        try {
            val response = api.getByUid(uid)

            return when {
                response.isSuccessful -> {
                    val nombreMesa = response.body()

                    if (nombreMesa != null && nombreMesa.isNotBlank()) {
                        TableStatus.Success(nombreMesa)
                    } else {
                        TableStatus.ApiError
                    }
                }
                response.code() == 409 -> TableStatus.Occupied
                else -> TableStatus.ApiError
            }
        } catch (e: Exception) {
            return TableStatus.ApiError
        }
    }
}

sealed class TableStatus {
    data class Success(val nombreMesa: String) : TableStatus()
    data object Occupied : TableStatus()
    data object ApiError : TableStatus()
}
