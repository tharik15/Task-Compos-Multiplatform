package org.example.todotask.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.todotask.data.models.Priority

interface DataStoreRepository {
    suspend fun persistSortState(priority: Priority)
    val readStoreState: Flow<String>
}