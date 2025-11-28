package com.example.myapplication.domain.drink

import com.example.myapplication.data.model.DrinkModel
import com.example.myapplication.data.repository.DrinkRepository
import javax.inject.Inject

class GetDrinkByIdUseCase @Inject constructor(
    private val repository: DrinkRepository
) {
    suspend operator fun invoke(id: String): DrinkModel? = repository.getDrinkById(id)
}