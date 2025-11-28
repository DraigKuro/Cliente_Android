package com.example.myapplication.domain.promotion

import com.example.myapplication.data.model.PromotionModel
import com.example.myapplication.data.repository.PromotionRepository
import javax.inject.Inject

class GetPromotionsUseCase @Inject constructor(
    private val repository: PromotionRepository
) {
    suspend operator fun invoke(): List<PromotionModel> = repository.getAllPromotions()
}