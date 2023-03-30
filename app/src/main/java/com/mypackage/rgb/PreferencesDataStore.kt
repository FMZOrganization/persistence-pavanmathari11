//Name: Pavan Kumar Mathari
//CWID: 885186924
//E-mail: pavan.mathari@csu.fullerton.edu
package com.mypackage.rgb


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.*

class PreferencesDataStore private constructor(private val dataStore: DataStore<Preferences>) {


    val redEditText: Flow<String> = this.dataStore.data.map { prefs ->
        prefs[retk] ?: INITIAL_COUNTER_VALUE
    }.distinctUntilChanged()
    val greenEditText: Flow<String> = this.dataStore.data.map { prefs ->
        prefs[getk] ?: INITIAL_COUNTER_VALUE
    }.distinctUntilChanged()
    val blueEditText: Flow<String> = this.dataStore.data.map { prefs ->
        prefs[betk] ?: INITIAL_COUNTER_VALUE
    }.distinctUntilChanged()


    val redSwitch: Flow<String> = this.dataStore.data.map { prefs ->
        prefs[rs] ?: INITIAL_COUNTER_VALUE
    }.distinctUntilChanged()
    val greenSwitch: Flow<String> = this.dataStore.data.map { prefs ->
        prefs[gs] ?: INITIAL_COUNTER_VALUE
    }.distinctUntilChanged()
    val blueSwitch: Flow<String> = this.dataStore.data.map { prefs ->
        prefs[bs] ?: INITIAL_COUNTER_VALUE
    }.distinctUntilChanged()




    private suspend fun saveStringValue(key: Preferences.Key<String>, value: String) {
        this.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    suspend fun saveInput(value:String, index: Int){
        val key: Preferences.Key<String> = when (index) {
            1 -> retk
            2 -> getk
            3 -> betk
            4 -> rs
            5 -> gs
            6 -> bs

            else -> {
                throw NoSuchFieldException("invalid input for index: $index")
            }
        }
        this.saveStringValue(key,value)
    }

    companion object {
        private val retk = stringPreferencesKey("redEditText")
        private val getk = stringPreferencesKey("greenEditText")
        private val betk = stringPreferencesKey("blueEditText")

        private val rs = stringPreferencesKey("redSwitch")
        private val gs = stringPreferencesKey("greenSwitch")
        private val bs = stringPreferencesKey("blueSwitch")


        private const val PREFERENCES_DATA_FILE_NAME = "settings"
        private var INSTANCE: PreferencesDataStore? = null


        fun initialize(context: Context) {
            if (INSTANCE == null) {
                val dataStore = PreferenceDataStoreFactory.create {
                    context.preferencesDataStoreFile(PREFERENCES_DATA_FILE_NAME)
                }
                INSTANCE = PreferencesDataStore(dataStore)
            }
        }
        fun getRepository(): PreferencesDataStore {
            return INSTANCE ?: throw IllegalStateException("AppPreferencesRepository not initialized yet")

        }
    }
}