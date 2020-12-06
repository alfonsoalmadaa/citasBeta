package com.example.citas.ui.notificaciones


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

class NotificacionesFragment : Fragment() {

    val user = "default"
    val days = 7

    lateinit var personaAdapter: NotificacionesAdapter
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notificaciones, container, false)
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val databaseAccess = DatabaseAccess.getInstance(this.requireContext())
        databaseAccess!!.open()
        val nots = databaseAccess!!.getNotifications(user,days)
        databaseAccess!!.close()

        val personaList: MutableList<NotificacionesViewModel> = mutableListOf<NotificacionesViewModel>()
        for(i in 0 until (nots.size-1) step 4){
            personaList.add(NotificacionesViewModel(
                    nots[i]+" "+nots[i+1], nots[i+2], nots[i+3]))
        }

        personaAdapter = NotificacionesAdapter(requireActivity().applicationContext, personaList)
        val recyclerViewPersonas= view.findViewById<RecyclerView>(R.id.recycleViewNots)
        recyclerViewPersonas.adapter = personaAdapter
        recyclerViewPersonas.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerViewPersonas.smoothScrollToPosition(personaList.size)

    }

}




