package wangyeo.interview.data.repositories

import kotlinx.coroutines.flow.Flow
import wangyeo.interview.data.local.room.HistorySearchAddressEntity

interface UserRepository {
    fun getSearchAddress(): Flow<List<HistorySearchAddressEntity>>

    suspend fun addSearchAddress(historySearchAddressEntity: HistorySearchAddressEntity)

    suspend fun clearAllSearchAddress()
}
