package uz.dsavdo.emakro.network.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class DataStoreRepository @Inject constructor(@ApplicationContext val context: Context) {

    companion object {
        const val PREF = "settings"
        const val CHANGE_LANGUAGE = "CHANGE_LANGUAGE"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREF)

    val language = get(CHANGE_LANGUAGE, String::class.java)
    suspend fun onLanguageChange(language: String) = put(CHANGE_LANGUAGE, language)

    private suspend fun <T> put(key: String, data: T) {
        context.dataStore.edit {
            when (data) {
                is String -> it[stringPreferencesKey(key)] = data
                is Boolean -> it[booleanPreferencesKey(key)] = data
                is Float -> it[floatPreferencesKey(key)] = data
                is Double -> it[doublePreferencesKey(key)] = data
                is Int -> it[intPreferencesKey(key)] = data
                is Long -> it[longPreferencesKey(key)] = data
            }
        }
    }

    private fun <T> get(key: String, clazz: Class<T>): Flow<T> = context.dataStore.data
        .catch {
            if (it is IOException) {
                Log.e("DataStore", it.message.toString())
                emit(emptyPreferences())
            } else throw it
        }.map {
            when (clazz) {
                String::class.java -> it[stringPreferencesKey(key)] ?: ""
                Boolean::class.java -> it[booleanPreferencesKey(key)] ?: false
                Float::class.java -> it[floatPreferencesKey(key)] ?: 0f
                Double::class.java -> it[doublePreferencesKey(key)] ?: 0L
                Int::class.java -> it[intPreferencesKey(key)] ?: -1
                Long::class.java -> it[longPreferencesKey(key)] ?: 0
                else -> null
            } as T
        }
}