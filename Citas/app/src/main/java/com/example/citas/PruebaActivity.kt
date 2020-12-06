package com.example.citas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_prueba.*
import kotlinx.android.synthetic.main.activity_prueba2.*

class PruebaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prueba2)

        val bundle = intent.extras
        if(bundle != null){
            val nombre = bundle.getString("name")
            val correo = bundle.getString("email")
            tv_nombre.setText(nombre)
            tv_email.setText(correo)

        }

    }
}