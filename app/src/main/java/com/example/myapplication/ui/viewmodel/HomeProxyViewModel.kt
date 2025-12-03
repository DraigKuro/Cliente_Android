package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeProxyViewModel @Inject constructor(
    tableViewModel: TableViewModel
) : ViewModel() {
    val tableState = tableViewModel.state
}