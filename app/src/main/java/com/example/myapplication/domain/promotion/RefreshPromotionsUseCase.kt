package com.example.myapplication.domain.promotion

import com.example.myapplication.data.repository.PromotionRepository
import javax.inject.Inject

class RefreshPromotionsUseCase @Inject constructor(
    private val repository: PromotionRepository
) {
    suspend operator fun invoke() = repository.refresh()
}