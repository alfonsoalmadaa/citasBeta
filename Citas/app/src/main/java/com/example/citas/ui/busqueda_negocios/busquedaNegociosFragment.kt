package com.example.citas.ui.busqueda_negocios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.citas.R
import com.example.citas.ui.DatabaseAccess
import com.example.citas.ui.citas.CitasViewModel

class busquedaNegociosFragment : Fragment(){
    val user = "default"
    val days = 20

    lateinit var personaAdapter: busquedaNegociosAdapter
    private lateinit var busquedaNegociosViewModel: CitasViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_busquedanegocios, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewPersonas= view.findViewById<RecyclerView>(R.id.recycleViewbusquedaNegocios)
        val personaList: MutableList<busquedaNegociosViewModel> = mutableListOf<busquedaNegociosViewModel>()

        val locales = requireArguments().getStringArray("data")
        if (locales != null) {
            val databaseAccess = DatabaseAccess.getInstance(this.requireContext())
            databaseAccess!!.open()
            for(i in 0 until (locales.size) step 2){
                val info_site = databaseAccess!!.getInfoSite(locales[i])
                personaList.add(
                        busquedaNegociosViewModel(
                                info_site[0], info_site[1], locales[i + 1] + " metros de tu dirección", locales[i])
                )
            }
            databaseAccess!!.close()
        }
        personaAdapter = busquedaNegociosAdapter(requireActivity().applicationContext, personaList)
        recyclerViewPersonas.adapter = personaAdapter
        recyclerViewPersonas.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerViewPersonas.smoothScrollToPosition(personaList.size)



    }


}
