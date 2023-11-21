package com.mkiperszmid.emptyapp.saludo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SaludoViewModel(
    private val  apiSaludo: ApiSaludoService
) : ViewModel() {
    var state by mutableStateOf(SaludoState())
        private set

    init {

    }

    val salida:MutableState<String> = mutableStateOf("....")
    val salida2:MutableState<String> = mutableStateOf("....")

    fun test() {
        viewModelScope.launch {
            try {
                salida.value = apiSaludo.test()
            } catch (e: Exception) {
                println("Error: " + e.message)
                salida.value = "Error: " + e.message
            }
        }
    }

    fun getSaludo() {
        viewModelScope.launch {
            try {
                salida2.value = apiSaludo.getSaludo()
            } catch (e: Exception) {
                println("Error: " + e.message)
                salida2.value = "Error: " + e.message
            }
        }
    }


}