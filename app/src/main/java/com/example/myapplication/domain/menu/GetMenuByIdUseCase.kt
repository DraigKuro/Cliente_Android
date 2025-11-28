package com.example.myapplication.domain.menu

import com.example.myapplication.data.model.MenuModel
import com.example.myapplication.data.repository.MenuRepository
import javax.inject.Inject

class GetMenuByIdUseCase @Inject constructor(
    private val repository: MenuRepository
) {
    suspend operator fun invoke(id: String): MenuModel? = repository.getMenuById(id)
}