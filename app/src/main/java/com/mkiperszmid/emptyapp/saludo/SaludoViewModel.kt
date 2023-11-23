package com.mkiperszmid.emptyapp.saludo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkiperszmid.emptyapp.graph.ErrorGraph
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Callback
import retrofit2.Response

class SaludoViewModel(
    private val  apiSaludo: ApiSaludoService
) : ViewModel() {
    var state by mutableStateOf(SaludoState())
        private set

    init {

    }

    val mostarLoading:MutableState<Boolean> = mutableStateOf(false)

    val salida:MutableState<String> = mutableStateOf("....")
    val salida2:MutableState<String> = mutableStateOf("....")

    fun test() {
        viewModelScope.launch {
            mostarLoading.value = true
            try {
                val call = apiSaludo.test()
                call.enqueue(object : Callback<String> {
                    override fun onResponse(call: retrofit2.Call<String>, response: Response<String>) {
                        if(response.isSuccessful) {
                            salida.value = response.body()!!
                        } else {
                            salida.value = "Error (" + response.code().toString() + "): " + response.message()
                        }
                        mostarLoading.value = false
                    }

                    override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                        salida.value = "Error: " + t.message
                        mostarLoading.value = false
                    }
                })
            } catch (e: Exception) {
                println("Error: " + e.message)
                salida.value = "Error: " + e.message
                mostarLoading.value = false
            }
        }
    }

    fun getSaludo() {
        viewModelScope.launch {
            mostarLoading.value = true
            try {
                val call = apiSaludo.getSaludo()
                call.enqueue(object : Callback<String> {
                    override fun onResponse(call: retrofit2.Call<String>, response: Response<String>) {
                        if(response.isSuccessful) {
                            salida2.value = response.body()!!
                        } else {
                            salida2.value = "Error (" + response.code().toString() + "): " + response.message()
                        }
                        mostarLoading.value = false
                    }

                    override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                        salida2.value = "Error: " + t.message
                        mostarLoading.value = false
                    }
                })

            } catch (e: Exception) {
                println("Error: " + e.message)
                salida2.value = "Error: " + e.message
                mostarLoading.value = false
            }
        }
    }

}