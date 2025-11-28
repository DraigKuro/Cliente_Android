package com.example.myapplication.domain.dish

import com.example.myapplication.data.model.DishModel
import com.example.myapplication.data.repository.DishRepository
import javax.inject.Inject

class GetDishByIdUseCase @Inject constructor(
    private val repository: DishRepository
) {
    suspend operator fun invoke(id: String): DishModel? = repository.getDishById(id)
}