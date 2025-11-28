package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.TableStatus
import com.example.myapplication.domain.table.ConnectTableUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TableViewModel @Inject constructor(
    private val connectTableUseCase: ConnectTableUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TableState())
    val state: StateFlow<TableState> = _state.asStateFlow()

    fun connectTableByUid(uid: String) {
        _state.update {
            it.copy(isLoading = true, error = null, nombreMesa = null)
        }

        viewModelScope.launch {
            val status = connectTableUseCase(uid)

            _state.update { currentState ->
                when (status) {
                    is TableStatus.Success -> {
                        currentState.copy(
                            nombreMesa = status.nombreMesa,
                            isLoading = false,
                            error = null
                        )
                    }
                    TableStatus.Occupied -> {
                        currentState.copy(
                            isLoading = false,
                            error = "La mesa ya está ocupada."
                        )
                    }
                    TableStatus.ApiError -> {
                        currentState.copy(
                            isLoading = false,
                            error = "Error de conexión o servidor. Intente de nuevo."
                        )
                    }
                }
            }
        }
    }
}

data class TableState(
    val nombreMesa: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)