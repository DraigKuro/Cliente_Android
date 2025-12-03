package com.example.myapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TableViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(TableState())
    val state: StateFlow<TableState> = _state.asStateFlow()

    fun setTableConnected(uid: String, nombreMesa: String) {
        Log.d("TableViewModel", "Mesa conectada! uid=$uid | nombreMesa='$nombreMesa'")
        _state.update {
            it.copy(
                uid = uid,
                nombreMesa = nombreMesa,
                isConnected = true
            )
        }
    }

    fun updateOrderSummary(totalItems: Int, totalPrice: Double) {
        _state.update {
            it.copy(
                orderSummary = OrderSummary(totalItems, totalPrice)
            )
        }
    }
}

data class OrderSummary(
    val totalItems: Int = 0,
    val totalPrice: Double = 0.0
)

data class TableState(
    val isConnected: Boolean = false,
    val uid: String? = null,
    val nombreMesa: String? = null,
    val orderSummary: OrderSummary = OrderSummary()
)