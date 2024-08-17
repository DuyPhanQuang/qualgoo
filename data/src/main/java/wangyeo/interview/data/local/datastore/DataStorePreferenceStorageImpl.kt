package wangyeo.interview.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

class DataStorePreferenceStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStorePreferenceStorage {
    companion object {
        const val PREFS_NAME = "app_datastore"
    }
}