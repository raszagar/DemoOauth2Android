package com.mkiperszmid.emptyapp.saludo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkiperszmid.emptyapp.graph.Datos
import kotlinx.coroutines.launch

class GraphViewModel(
    private val  apiGraph: ApiGraphService
) : ViewModel() {
    var state by mutableStateOf(GraphState())
        private set

    init {

    }

    val salida:MutableState<String> = mutableStateOf("....")

    fun datos() {
        viewModelScope.launch {
            try {
                var datos:Datos = apiGraph.datos()
                println(datos)
                salida.value = datos.toString()
            } catch (e: Exception) {
                println("Error: " + e.message)
                salida.value = "Error: " + e.message
            }
        }
    }


}