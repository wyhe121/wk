package com.example.projectdemo.network.dao

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.projectdemo.App.Companion.mApplication
import com.example.projectdemo.data.model.bean.ApiPagerResponse
import com.example.projectdemo.data.model.bean.AriticleResponse
import com.example.projectdemo.ext.dataStore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.prefs.Preferences
import kotlin.reflect.KClass
import com.google.gson.reflect.TypeToken




object  DataStoreBase {

    suspend fun getString(key: String, defaultValue: String? = null): String? {
        return mApplication.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)] ?: defaultValue
        }.first()
    }

    suspend fun getInt(key: String, defaultValue: Int? = null): Int? {
        return mApplication.dataStore.data.map { preferences ->
            preferences[intPreferencesKey(key)] ?: defaultValue
        }.first()
    }

    suspend fun getDouble(key: String, defaultValue: Double? = null): Double? {
        return mApplication.dataStore.data.map { preferences ->
            preferences[doublePreferencesKey(key)] ?: defaultValue
        }?.first()
    }

    suspend fun getBoolean(key: String, defaultValue: Boolean? = null): Boolean? {
        return mApplication.dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(key)] ?: defaultValue
        }?.first()
    }

    suspend fun getFloat(key: String, defaultValue: Float? = null): Float? {
        return mApplication.dataStore.data.map { preferences ->
            preferences[floatPreferencesKey(key)] ?: defaultValue
        }?.first()
    }
    suspend fun isSave(key: String): Boolean {
        return mApplication.dataStore.edit {

        }.contains(stringPreferencesKey(key))
    }
    suspend fun getLong(key: String, defaultValue: Long? = null): Long? {
        return mApplication.dataStore.data.map { preferences ->
            preferences[longPreferencesKey(key)] ?: defaultValue
        }?.first()
    }

    suspend fun getStringSet(key: String, defaultValue: Set<String>? = null): Set<String>? {
        return mApplication.dataStore.data.map { preferences ->
            preferences[stringSetPreferencesKey(key)] ?: defaultValue
        }?.first()
    }

    suspend fun <T> getObject(key: String, clazz: Class<T>, defaultValue: T? = null): T? {
        return mApplication.dataStore.data.catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val a = preferences[stringPreferencesKey(key)]
            if (a != null) a as T else defaultValue

        }.first()
    }

    suspend fun <T : Any> get(key: String, clazz:Class<T>, defaultValue: T? = null): T? {
        Log.d("a3","1")
        val pKey = when (clazz) {
            String::class -> {
                Log.d("a3","2")
                stringPreferencesKey(key)
            }
            Int::class -> {
                Log.d("a3","3")
                intPreferencesKey(key)
            }
            Double::class -> {
                Log.d("a3","4")
                doublePreferencesKey(key)
            }
            Boolean::class -> {
                Log.d("a3","5")
                booleanPreferencesKey(key)
            }
            Float::class -> {
                Log.d("a3","6")
                floatPreferencesKey(key)
            }
            Long::class -> {
                Log.d("a3","7")
                longPreferencesKey(key)
            }
            Set::class -> {
                Log.d("a3","8")
                stringSetPreferencesKey(key)
            }
            else -> {
                Log.d("a3","9")
                null
            }
        }
        return mApplication?.dataStore?.data.map { preferences ->
            if (pKey != null) {
                Log.d("a3","10")
                (preferences[pKey] as? T) ?: defaultValue
            } else {
                Log.d("a3","11")
                val a = preferences[stringPreferencesKey(key)]
                if (a != null) {
                    Log.d("a3","12")

                  Gson().fromJson(a,clazz)
                } else {
                    Log.d("a3","13")
                    defaultValue
                }
            }
        }?.first()
    }

    suspend fun put(key: String, value: Any) {
        when (value::class) {
            String::class -> {
                mApplication.dataStore.edit { settings ->
                    settings[stringPreferencesKey(key)] = value as String
                }
            }
            Int::class -> {
                mApplication.dataStore.edit { settings ->
                    settings[intPreferencesKey(key)] = value as Int
                }
            }
            Double::class -> {
                mApplication.dataStore.edit { settings ->
                    settings[doublePreferencesKey(key)] = value as Double
                }
            }
            Boolean::class -> {
                mApplication.dataStore.edit { settings ->
                    settings[booleanPreferencesKey(key)] = value as Boolean
                }
            }
            Float::class -> {
                mApplication.dataStore.edit { settings ->
                    settings[floatPreferencesKey(key)] = value as Float
                }
            }
            Long::class -> {
                mApplication.dataStore.edit { settings ->
                    settings[longPreferencesKey(key)] = value as Long
                }
            }
            Set::class -> {
                mApplication.dataStore.edit { settings ->
                    settings[stringSetPreferencesKey(key)] = value as Set<String>
                }
            }
            else -> {
                mApplication.dataStore.edit { settings ->
                    settings[stringPreferencesKey(key)] = Gson().toJson(value)
                }
            }
        }
    }
}
