package wangyeo.interview.data.repositories.impl

import kotlinx.coroutines.flow.Flow
import wangyeo.interview.data.local.room.HistorySearchAddressDao
import wangyeo.interview.data.local.room.HistorySearchAddressEntity
import wangyeo.interview.data.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val searchAddressDao: HistorySearchAddressDao,
) : UserRepository {
    override suspend fun addSearchAddress(historySearchAddressEntity: HistorySearchAddressEntity) {
        searchAddressDao.insertOrUpdate(historySearchAddressEntity)
    }

    override suspend fun clearAllSearchAddress() = searchAddressDao.deleteAll()

    override fun getSearchAddress(): Flow<List<HistorySearchAddressEntity>> = searchAddressDao.getAll()
}
