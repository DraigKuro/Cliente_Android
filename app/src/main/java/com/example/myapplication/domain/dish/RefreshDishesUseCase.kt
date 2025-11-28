package com.example.myapplication.domain.dish

import com.example.myapplication.data.repository.DishRepository
import javax.inject.Inject

class RefreshDishesUseCase @Inject constructor(
    private val repository: DishRepository
) {
    suspend operator fun invoke() = repository.refresh()
}