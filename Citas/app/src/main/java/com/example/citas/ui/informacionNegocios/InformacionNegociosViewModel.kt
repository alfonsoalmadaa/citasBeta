package com.example.citas.ui.informacionNegocios

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class InformacionNegociosViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is configuration Fragment"
    }
    val text: LiveData<String> = _text
}