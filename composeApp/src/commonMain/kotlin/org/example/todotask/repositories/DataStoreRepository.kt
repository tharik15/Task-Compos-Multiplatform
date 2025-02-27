//package org.example.todotask.repositories
//
//import androidx.datastore.core.DataStore
//import androidx.datastore.core.IOException
//import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.core.emptyPreferences
//import androidx.datastore.preferences.core.stringPreferencesKey
//import androidx.datastore.preferences.preferencesDataStore
//import org.example.todotask.util.Constants.PREFERENCE_KEY
//import org.example.todotask.util.Constants.PREFERENCE_NAME
//import kotlinx.coroutines.flow.catch
//import kotlinx.coroutines.flow.map
//import org.example.todotask.data.models.Priority
//import org.koin.core.scope.Scope
//
//val Scope.dataStore:DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)
//
//class DataStoreRepository constructor(
//    context:Scope
//){
//
//    private object PreferenceKey{
//        val sortedKey = stringPreferencesKey(PREFERENCE_KEY)
//    }
//
//    val dataStore = context.dataStore
//
//    suspend fun persistSortState(priority: Priority){
//        dataStore.edit { preference ->
//            preference[PreferenceKey.sortedKey] = priority.name
//        }
//    }
//
//    val readStoreState:kotlinx.coroutines.flow.Flow<String> = dataStore.data
//        .catch { exception ->
//            if (exception is IOException) {
//                emit(emptyPreferences())
//            }else{
//                throw exception
//            }
//    }.map { preference ->
//        preference[PreferenceKey.sortedKey] ?: Priority.NONE.name
//    }
//}