package com.example.citas


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_prueba.*
import kotlinx.android.synthetic.main.activity_prueba2.*
import kotlinx.android.synthetic.main.activity_prueba2.tv_nombre
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.nav_header_main.*

class headerNav : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.nav_header_main, container, false)
        return root


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val incomingText = ACTIVITY.nombre
        val correo = ACTIVITY.correo
        tv_nombrenav.setText(incomingText)
        tv_navemail.setText(correo)


    }
}