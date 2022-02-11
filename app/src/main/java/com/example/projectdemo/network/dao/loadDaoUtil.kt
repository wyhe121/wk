package com.example.projectdemo.network.dao

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.projectdemo.App.Companion.mApplication

import com.example.projectdemo.appContext
import com.example.projectdemo.data.model.bean.AriticleResponse
import com.example.projectdemo.ext.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.prefs.Preferences
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

object loadDaoUtil {


    val EXAMPLE_COUNTER = intPreferencesKey("example_counter")

    val EXAMPLE_COUNTER2 = stringPreferencesKey("example_counter")

    val exampleCounterFlow: Flow<Int> = mApplication.dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[EXAMPLE_COUNTER] ?: 0
        }

    suspend fun incrementCounte2r() {
        mApplication.dataStore.edit { settings ->

            settings[EXAMPLE_COUNTER2] = "currentCounterValue + 1"
        }
    }
    suspend fun incrementCounter() {
        mApplication.dataStore.edit { settings ->
            val currentCounterValue = settings[EXAMPLE_COUNTER] ?: 0
            settings[EXAMPLE_COUNTER] = currentCounterValue + 1
        }
    }


}


