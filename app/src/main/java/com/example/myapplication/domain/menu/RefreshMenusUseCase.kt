package com.example.myapplication.domain.menu

import com.example.myapplication.data.repository.MenuRepository
import javax.inject.Inject

class RefreshMenusUseCase @Inject constructor(
    private val repository: MenuRepository
) {
    suspend operator fun invoke() = repository.refresh()
}