package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrScanViewModel @Inject constructor(
    private val tableViewModel: TableViewModel
) : ViewModel() {

    private val ENABLE_MOCK = true
    private val MOCK_UID = "388a92f5-2dda-4577-90c8-6333aad41fa2"

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

        tableViewModel.connectTableByUid(uid)

        viewModelScope.launch {
            tableViewModel.state.collect { state ->
                when {
                    state.nombreMesa != null -> {
                        _uiState.value = QrScanState.Success(state.nombreMesa)
                    }
                    state.error != null -> {
                        _uiState.value = QrScanState.Error(state.error)
                    }
                }
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