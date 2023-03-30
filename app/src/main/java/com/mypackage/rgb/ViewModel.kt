//Name: Pavan Kumar Mathari
//CWID: 885186924
//E-mail: pavan.mathari@csu.fullerton.edu

package com.mypackage.rgb

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


const val INITIAL_COUNTER_VALUE = ""

class viewModel : ViewModel() {

    private val prefs = PreferencesDataStore.getRepository()

    fun saveInput(s: String, index: Int) {
        viewModelScope.launch {
            prefs.saveInput(s, index)
            Log.d("MainActivity", "Inputs Saved")
        }
    }

    fun loadInputs(act: MainActivity) {
        viewModelScope.launch {
            prefs.redEditText.collectLatest {
                act.redEditText.setText(it.toString())
                Log.d("MainActivity", "Collected RedEditText Value")
            }
        }
        viewModelScope.launch {
            prefs.greenEditText.collectLatest {
                act.greenEditText.setText(it.toString())
                Log.d("MainActivity", "Collected GreenEditText Value")
            }
        }
        viewModelScope.launch {
            prefs.blueEditText.collectLatest {
                act.blueEditText.setText(it.toString())
                Log.d("MainActivity", "Collected BlueEditText Value")
            }
        }
        viewModelScope.launch {
            prefs.redSwitch.collectLatest {
                act.redSwitch.setChecked(it.toBoolean())
                Log.d("MainActivity", "Collected RedSwitch Value")
            }
        }
        viewModelScope.launch {
            prefs.greenSwitch.collectLatest {
                act.greenSwitch.setChecked(it.toBoolean())
                Log.d("MainActivity", "Collected GreenSwitch Value")
            }
        }

        viewModelScope.launch {
            prefs.blueSwitch.collectLatest {
                act.blueSwitch.setChecked(it.toBoolean())
                //act.blueSwitch.setText(it.toString())
                Log.d("MainActivity", "Collected BlueSwitch Value")
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MainActivity", "Called OnCleared")
    }
}
