package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.ItemModel
import com.example.myapplication.data.model.OrderItemRequest
import com.example.myapplication.data.model.PromotionModel
import com.example.myapplication.domain.order.CreateOrderUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartViewModel @Inject constructor(
    private val createOrderUseCase: CreateOrderUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state.asStateFlow()

    private val _orderUiState = MutableStateFlow<OrderUiState>(OrderUiState.Idle)
    val orderUiState: StateFlow<OrderUiState> = _orderUiState.asStateFlow()

    fun confirmOrder(tableId: String?, onSuccess: (List<OrderItem>) -> Unit) {
        val currentItems = _state.value.items
        if (currentItems.isEmpty()) return

        viewModelScope.launch {
            _orderUiState.value = OrderUiState.Loading

            val orderItemsRequest = currentItems.map {
                OrderItemRequest(_id = it.item.id, cantidad = it.cantidad)
            }

            val result = createOrderUseCase(tableId, orderItemsRequest)

            if (result != null && result.success) {
                val confirmedItems = currentItems.map {
                    OrderItem(it.item.id, it.item.nombre, it.cantidad, it.item.precio)
                }

                _orderUiState.value = OrderUiState.Success(result.message)

                onSuccess(confirmedItems)

                clearCart()
            } else {
                _orderUiState.value = OrderUiState.Error("Error al procesar el pedido")
            }
        }
    }

    fun resetOrderState() {
        _orderUiState.value = OrderUiState.Idle
    }
    fun addItemToCart(item: ItemModel, cantidad: Int) {
        _state.update { currentState ->
            val existingItem = currentState.items.find { it.item.id == item.id }

            val maxLimit = if (item is PromotionModel) item.cantidad else 99

            val updatedItems = if (existingItem != null) {
                currentState.items.map { cartItem ->
                    if (cartItem.item.id == item.id) {
                        val newQuantity = (cartItem.cantidad + cantidad).coerceAtMost(maxLimit)
                        cartItem.copy(cantidad = newQuantity)
                    } else {
                        cartItem
                    }
                }
            } else {
                currentState.items + CartItem(item, cantidad.coerceAtMost(maxLimit))
            }

            currentState.copy(items = updatedItems)
        }
    }

    fun removeItemFromCart(id: String) {
        _state.update { currentState ->
            currentState.copy(items = currentState.items.filter { it.item.id != id })
        }
    }

    fun updateItemQuantity(id: String, cantidad: Int) {
        _state.update { currentState ->
            val updatedItems = currentState.items.map { cartItem ->
                if (cartItem.item.id == id) {
                    val maxLimit = if (cartItem.item is PromotionModel) {
                        cartItem.item.cantidad
                    } else {
                        99
                    }
                    val validQuantity = cantidad.coerceIn(1, maxLimit)
                    cartItem.copy(cantidad = validQuantity)
                } else {
                    cartItem
                }
            }
            currentState.copy(items = updatedItems)
        }
    }

    fun clearCart() {
        _state.update { CartState() }
    }

    fun getTotalItems(): Int = _state.value.items.sumOf { it.cantidad }

    fun getTotalPrice(): Double = _state.value.items.sumOf { it.cantidad * it.item.precio }
}

data class CartItem(
    val item: ItemModel,
    val cantidad: Int
)

data class CartState(
    val items: List<CartItem> = emptyList()
)

sealed class OrderUiState {
    object Idle : OrderUiState()
    object Loading : OrderUiState()
    data class Success(val message: String) : OrderUiState()
    data class Error(val message: String) : OrderUiState()
}