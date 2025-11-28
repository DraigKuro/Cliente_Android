package com.example.myapplication.domain.table

import com.example.myapplication.data.repository.TableRepository
import com.example.myapplication.data.repository.TableStatus
import javax.inject.Inject

class ConnectTableUseCase @Inject constructor(
    private val tableRepository: TableRepository
) {
    suspend operator fun invoke(uid: String): TableStatus {
        return tableRepository.getTableByUid(uid)
    }
}