package com.example.myapplication.domain.dish

import com.example.myapplication.data.model.DishModel
import com.example.myapplication.data.repository.DishRepository
import javax.inject.Inject

class GetPostresUseCase @Inject constructor(
    private val repository: DishRepository
) {
    suspend operator fun invoke(): List<DishModel> = repository.getPostres()
}