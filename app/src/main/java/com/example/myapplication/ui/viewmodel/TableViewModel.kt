package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.TableStatus
import com.example.myapplication.domain.table.CallWaiterUseCase
import com.example.myapplication.domain.table.RequestBillUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TableViewModel @Inject constructor(
    private val callWaiterUseCase: CallWaiterUseCase,
    private val requestBillUseCase: RequestBillUseCase
) : ViewModel() {

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

    fun llamarCamarero() {
        val uid = _state.value.uid ?: return
        viewModelScope.launch {
            _state.update { it.copy(isCallingWaiter = true) }
            val result = callWaiterUseCase(uid)
            _state.update { it.copy(isCallingWaiter = false) }
        }
    }

    fun pedirCuenta() {
        val uid = _state.value.uid ?: return
        viewModelScope.launch {
            _state.update { it.copy(isRequestingBill = true) }
            val result = requestBillUseCase(uid)
            _state.update { it.copy(isRequestingBill = false) }
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
    val confirmedOrders: List<OrderItem> = emptyList(),
    val isCallingWaiter: Boolean = false,
    val isRequestingBill: Boolean = false
)