package com.example.myapplication.domain.table

import com.example.myapplication.data.repository.TableRepository
import com.example.myapplication.data.repository.TableStatus
import javax.inject.Inject

class CallWaiterUseCase @Inject constructor(
    private val repository: TableRepository
) {
    suspend operator fun invoke(uid: String): TableStatus {
        return repository.callWaiter(uid)
    }
}