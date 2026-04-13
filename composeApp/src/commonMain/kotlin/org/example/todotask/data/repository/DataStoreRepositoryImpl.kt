package org.example.todotask.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.example.todotask.data.models.Priority
import org.example.todotask.domain.repository.DataStoreRepository
import org.example.todotask.util.Constants

class DataStoreRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository{

    private object PreferenceKey {
        val sortedKey = stringPreferencesKey(Constants.PREFERENCE_KEY)
    }

    override suspend fun persistSortState(priority: Priority) {
        dataStore.edit { preference ->
            preference[PreferenceKey.sortedKey] = priority.name
        }
    }

    override val readStoreState: Flow<String> = dataStore.data
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