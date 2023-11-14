package com.mkiperszmid.emptyapp.saludo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class SaludoState(
    val salidaTest: MutableState<String> = mutableStateOf("")
)
