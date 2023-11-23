package com.mkiperszmid.emptyapp.graph

import android.graphics.BitmapFactory
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Callback
import retrofit2.Response


class GraphViewModel(
    private val apiGraph: ApiGraphService
) : ViewModel() {
    var state by mutableStateOf(GraphState())
        private set

    val mostarLoading:MutableState<Boolean> = mutableStateOf(false)

    val salida:MutableState<String> = mutableStateOf("....")
    val salida2:MutableState<String> = mutableStateOf("....")
    val salida3:MutableState<String> = mutableStateOf("")
    val foto:MutableState<ImageBitmap?> = mutableStateOf(null)

    fun datos() {
        viewModelScope.launch {
            mostarLoading.value = true
            try {
                val call = apiGraph.datos()
                call.enqueue(object : Callback<Datos> {
                    override fun onResponse(call: retrofit2.Call<Datos>, response: Response<Datos>) {
                        if(response.isSuccessful) {
                            val datos:Datos = response.body()!!
                            println(datos)
                            salida.value = datos.toString()
                        } else {
                            salida2.value = "Error (" + response.code().toString() + "): " + response.message()
                        }
                        mostarLoading.value = false
                    }

                    override fun onFailure(call: retrofit2.Call<Datos>, t: Throwable) {
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

    fun datosFoto() {
        viewModelScope.launch {
            mostarLoading.value = true
            try {
                val response: retrofit2.Call<ResponseBody> = apiGraph.datosFoto()

                response.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: retrofit2.Call<ResponseBody>, response: Response<ResponseBody>) {
                        if(response.isSuccessful){
                            //Atención: sólo se puede leer una vez el response con body() o errorBody()
                            val body = response.body()?.string()

                            val fotoGraph: FotoGraph = Gson().fromJson<FotoGraph>(body, FotoGraph::class.java)

                            println(body)
                            salida2.value = fotoGraph.toString()
                        } else {
                            val errorBody: String = response.errorBody()!!.string()
                            println("Error: " + errorBody)

                            val error: ErrorGraph = errorHandle(errorBody)
                            salida2.value = error.toString()
                        }
                        mostarLoading.value = false
                    }

                    override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                        println("Error: " + t.message)
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

    fun verFoto() {
        viewModelScope.launch {
            mostarLoading.value = true
            try {
                val response: retrofit2.Call<ResponseBody> = apiGraph.verFoto()
                response.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: retrofit2.Call<ResponseBody>, response: Response<ResponseBody>) {
                        if(response.isSuccessful){
                            //Atención: sólo se puede leer una vez el response con body() o errorBody()
                            val bytesFoto = response.body()?.bytes()

                            if (bytesFoto != null) {
                                mostrarFoto(bytesFoto)
                            }
                        } else {
                            val errorBody: String = response.errorBody()!!.string()
                            println("Error: " + errorBody)

                            val error: ErrorGraph = errorHandle(errorBody)
                            salida3.value = error.toString()
                        }
                        mostarLoading.value = false
                    }

                    override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                        println("Error: " + t.message)
                        salida3.value = "Error: " + t.message
                        mostarLoading.value = false
                    }
                })

            } catch (e: Exception) {
                println("Error: " + e.message)
                salida3.value = "Error: " + e.message
                mostarLoading.value = false
            }
        }
    }

    fun errorHandle(response: String): ErrorGraph {
        try {
            println("->" + response)

            val errorGraph: ErrorGraph = Gson().fromJson<ErrorGraph>(response, ErrorGraph::class.java)

            return errorGraph
        } catch (e: Exception) {
            println("Error: " + e.message)
            throw e
        }
    }

    fun mostrarFoto(bytesFoto:ByteArray) {
        try {
            val decodeBitmap = BitmapFactory.decodeByteArray(bytesFoto, 0, bytesFoto.size)
            val imgBit = decodeBitmap?.asImageBitmap()
            foto.value = imgBit
        } catch (e: Exception) {
            println("Error: " + e.message)
            throw e
        }
    }

}