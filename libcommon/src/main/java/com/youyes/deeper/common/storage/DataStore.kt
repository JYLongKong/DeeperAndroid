package com.youyes.deeper.common.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by LGJY on 2021/9/15.
 * Email：yujye@sina.com
 *
 * DataStore from Jetpack
 */

/**
 * get dataStore by context anywhere and it will keep as singleton
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "lgjy")

class DataStore(private val context: Context) {

    fun getInt(key: String, defValue: Int = 0): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[intPreferencesKey(key)] ?: defValue
        }
    }

    suspend fun setInt(key: String, value: Int) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[intPreferencesKey(key)] = value
        }
    }
}
