package org.example.todotask.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import org.example.todotask.util.Constants.PREFERENCE_KEY
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.example.todotask.data.models.Priority

class DataStoreRepository(
    private val dataStore: DataStore<Preferences>
) {

    private object PreferenceKey {
        val sortedKey = stringPreferencesKey(PREFERENCE_KEY)
    }

    suspend fun persistSortState(priority: Priority) {
        dataStore.edit { preference ->
            preference[PreferenceKey.sortedKey] = priority.name
        }
    }

    val readStoreState: kotlinx.coroutines.flow.Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preference ->
            preference[PreferenceKey.sortedKey] ?: Priority.NONE.name
        }
}