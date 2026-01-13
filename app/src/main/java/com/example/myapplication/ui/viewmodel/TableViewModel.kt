package com.example.myapplication.ui.viewmodel

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
        _state.update {
            it.copy(
                uid = uid,
                nombreMesa = nombreMesa,
                isConnected = true
            )
        }
    }

    fun confirmOrder(items: List<OrderItem>) {
        _state.update { currentState ->
            currentState.copy(
                confirmedOrders = currentState.confirmedOrders + items
            )
        }
    }
}

data class OrderItem(
    val id: String,
    val nombre: String,
    val cantidad: Int,
    val precio: Double
)

data class TableState(
    val isConnected: Boolean = false,
    val uid: String? = null,
    val nombreMesa: String? = null,
    val confirmedOrders: List<OrderItem> = emptyList()
)