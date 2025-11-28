package com.example.myapplication.domain.drink

import com.example.myapplication.data.model.DrinkModel
import com.example.myapplication.data.repository.DrinkRepository
import javax.inject.Inject

class GetDrinksUseCase @Inject constructor(
    private val repository: DrinkRepository
) {
    suspend operator fun invoke(): List<DrinkModel> = repository.getAllDrinks()
}