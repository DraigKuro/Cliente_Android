package com.example.myapplication.domain.menu
import com.example.myapplication.data.model.MenuModel
import com.example.myapplication.data.repository.MenuRepository
import javax.inject.Inject

class GetMenusUseCase @Inject constructor(
    private val repository: MenuRepository
) {
    suspend operator fun invoke(): List<MenuModel> = repository.getAllMenus()
}
