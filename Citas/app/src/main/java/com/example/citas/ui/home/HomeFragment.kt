package com.example.citas.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.citas.BaseFragment
import com.example.citas.Login
import com.example.citas.MainActivity
import com.example.citas.R
import com.example.citas.ui.DatabaseAccess
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel
    var user = "default"
    lateinit var personaAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val incomingText = ACTIVITY.nombre
        tv_nombre.setText(incomingText)
        user=incomingText

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        logOut.setOnClickListener {

            val intent = Intent(activity, Login::class.java)
            intent.putExtra("close",234)
            startActivity(intent)
        }


        val toCitas= view.findViewById<LinearLayout>(R.id.home_to_citas)
        val toNegocios= view.findViewById<LinearLayout>(R.id.home_to_negocios)

        toCitas.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_citas)
        }
        toNegocios.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_negocios)
        }


        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val today_date = sdf.format(Date())

        val databaseAccess = DatabaseAccess.getInstance(this.requireContext())
        databaseAccess!!.open()
        val appointments = databaseAccess!!.getTodayApps(today_date, user)
        databaseAccess!!.close()

        val personaList: MutableList<HomeViewModel> = mutableListOf<HomeViewModel>()
        for(i in 0 until (appointments.size-1) step 3){
            personaList.add(
                HomeViewModel(
                    appointments[i], appointments[i + 1], appointments[i + 2]
                )
            )
        }

        personaAdapter = HomeAdapter(requireActivity().applicationContext, personaList)
        val recyclerViewPersonsHome= view.findViewById<RecyclerView>(R.id.recycleViewHome)
        recyclerViewPersonsHome.adapter = personaAdapter
        recyclerViewPersonsHome.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerViewPersonsHome.smoothScrollToPosition(personaList.size)

    }




}