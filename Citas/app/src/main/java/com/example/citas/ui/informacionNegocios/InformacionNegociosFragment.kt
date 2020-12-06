package com.example.citas.ui.informacionNegocios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.citas.R

class InformacionNegociosFragment : Fragment() {

    private lateinit var informacionNegociosViewModel: InformacionNegociosViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        informacionNegociosViewModel =
                ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(InformacionNegociosViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_informacionnegocios, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val local = requireArguments().getString("data")
        Toast.makeText(this.requireContext(), local, Toast.LENGTH_SHORT).show()
    }
}