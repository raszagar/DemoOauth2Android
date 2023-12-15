package es.jose.emptyapp.graph

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class GraphState(
    val salidaTest: MutableState<String> = mutableStateOf("")
)
