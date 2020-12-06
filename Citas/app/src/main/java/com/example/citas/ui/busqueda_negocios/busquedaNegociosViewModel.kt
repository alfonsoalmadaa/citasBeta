package com.example.citas.ui.busqueda_negocios

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


data class busquedaNegociosViewModel(var local: String, var direccion: String, var distancia: String, var id_local : String)