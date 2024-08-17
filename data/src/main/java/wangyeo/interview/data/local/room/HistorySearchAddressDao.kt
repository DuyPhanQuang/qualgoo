package wangyeo.interview.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HistorySearchAddressDao {
    @Query("SELECT * FROM history_search_address ORDER BY timeSearch DESC")
    fun getAll(): Flow<List<HistorySearchAddressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(historySearchAddressEntity: HistorySearchAddressEntity): Long

    @Update
    suspend fun update(historySearchAddressEntity: HistorySearchAddressEntity): Int

    @Query("SELECT * FROM history_search_address WHERE addressName = :addressName")
    suspend fun getTheSameAddressName(addressName: String): List<HistorySearchAddressEntity>

    @Transaction
    suspend fun insertOrUpdate(searchAddress: HistorySearchAddressEntity) {
        val listSameAddress = getTheSameAddressName(searchAddress.addressName)
        if (listSameAddress.isEmpty()) {
            insert(searchAddress)
        } else {
            update(listSameAddress.first().copy(timeSearch = searchAddress.timeSearch))
        }
    }

    @Query("DELETE FROM history_search_address")
    suspend fun deleteAll()
}
