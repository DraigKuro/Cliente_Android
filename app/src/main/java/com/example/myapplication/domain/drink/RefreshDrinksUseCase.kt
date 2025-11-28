package com.example.myapplication.domain.drink

import com.example.myapplication.data.repository.DrinkRepository
import javax.inject.Inject

class RefreshDrinksUseCase @Inject constructor(
    private val repository: DrinkRepository
) {
    suspend operator fun invoke() = repository.refresh()
}