package com.example.citas.ui.negocios

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class NegociosViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Negocios Fragment"
    }
    val text: LiveData<String> = _text
}