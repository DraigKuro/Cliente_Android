package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.TableStatus
import com.example.myapplication.domain.table.ConnectTableUseCase
import com.example.myapplication.ui.viewmodel.QrScanState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrScanViewModel @Inject constructor(
    private val connectTableUseCase: ConnectTableUseCase,
    private val tableViewModel: TableViewModel
) : ViewModel() {
    private val ENABLE_MOCK = true
    private val MOCK_UID = "8dbe8983-8e27-400d-b6e6-d2231de783f8"

    private val _uiState = MutableStateFlow<QrScanState>(QrScanState.Idle)
    val uiState: StateFlow<QrScanState> = _uiState

    fun startQrScan() {
        if (ENABLE_MOCK) {
            // MODO MOCK:
            connectTable(MOCK_UID)
        } else {
            // MODO REAL:
            // launchQrScanner()
        }
    }

    fun onQrCodeScanned(uid: String) {
        connectTable(uid)
    }

    private fun connectTable(uid: String) {
        _uiState.value = QrScanState.Loading

        viewModelScope.launch {
            val status = connectTableUseCase(uid)

            when (status) {
                is TableStatus.Success -> {
                    tableViewModel.setTableConnected(uid, status.nombreMesa)
                    _uiState.value = Success(status.nombreMesa)
                }

                TableStatus.Occupied -> Error("Esta mesa ya está ocupada.")
                TableStatus.ApiError -> Error("Error del servidor.")
                TableStatus.AlertSuccess -> TODO()
            }
        }
    }


// ============ Escáner real ============
/*
private lateinit var qrLauncher: ActivityResultLauncher<ScanOptions>

fun setQrLauncher(launcher: ActivityResultLauncher<ScanOptions>) {
    qrLauncher = launcher
}

private fun launchRealQrScanner() {
    qrLauncher.launch(
        ScanOptions().apply {
            setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            setPrompt("Apunta al código QR de la mesa")
            setBeepEnabled(true)
        }
    )
}
*/
}

sealed class QrScanState {
    data object Idle : QrScanState()
    data object Loading : QrScanState()
    data class Success(val nombreMesa: String) : QrScanState()
    data class Error(val message: String) : QrScanState()
}