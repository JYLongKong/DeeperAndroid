package com.youyes.deeper.common.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.youyes.deeper.common.BuildConfig
import com.youyes.deeper.common.app.DeepApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Created by LGJY on 2021/9/15.
 * Email：yujye@sina.com
 *
 * DataStore from Jetpack
 */

/**
 * get dataStore by context anywhere and it will keep it single
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = BuildConfig.LIBRARY_PACKAGE_NAME)

object DataStore {

    /**
     * A data dictionary user for getting data synchronously
     * Example:
     * val exampleData = runBlocking { context.dataStore.data.first() }
     */
    suspend fun dict(): Preferences = DeepApplication.instance.dataStore.data.first()

    fun getInt(key: String, defValue: Int = 0): Flow<Int> = get(intPreferencesKey(key), defValue)

    fun getDouble(key: String, defValue: Double = 0.0): Flow<Double> = get(doublePreferencesKey(key), defValue)

    fun getString(key: String, defValue: String = ""): Flow<String> = get(stringPreferencesKey(key), defValue)

    fun getBoolean(key: String, defValue: Boolean = false): Flow<Boolean> = get(booleanPreferencesKey(key), defValue)

    fun getFloat(key: String, defValue: Float = 0f): Flow<Float> = get(floatPreferencesKey(key), defValue)

    fun getLong(key: String, defValue: Long = 0L): Flow<Long> = get(longPreferencesKey(key), defValue)

    fun getStringSet(key: String, defValue: Set<String> = setOf()): Flow<Set<String>> =
        get(stringSetPreferencesKey(key), defValue)

    suspend fun setInt(key: String, value: Int): Unit = set(intPreferencesKey(key), value)

    suspend fun setDouble(key: String, value: Double): Unit = set(doublePreferencesKey(key), value)

    suspend fun setString(key: String, value: String): Unit = set(stringPreferencesKey(key), value)

    suspend fun setBoolean(key: String, value: Boolean): Unit = set(booleanPreferencesKey(key), value)

    suspend fun setFloat(key: String, value: Float): Unit = set(floatPreferencesKey(key), value)

    suspend fun setLong(key: String, value: Long): Unit = set(longPreferencesKey(key), value)

    suspend fun setStringSet(key: String, value: Set<String>): Unit = set(stringSetPreferencesKey(key), value)

    private fun <T> get(preferencesKey: Preferences.Key<T>, defValue: T): Flow<T> {
        return DeepApplication.instance.dataStore.data.map { preferences ->
            preferences[preferencesKey] ?: defValue
        }
    }

    private suspend fun <T> set(preferencesKey: Preferences.Key<T>, value: T) {
        DeepApplication.instance.dataStore.edit { mutablePreferences ->
            mutablePreferences[preferencesKey] = value
        }
    }
}
