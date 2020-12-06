package com.example.citas.ui.citas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.citas.R
import com.example.citas.ui.DatabaseAccess
import com.example.citas.ui.notificaciones.NotificacionesAdapter
import com.example.citas.ui.notificaciones.NotificacionesViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.*

class CitasFragment : Fragment() {
    val user = "default"

    lateinit var personaAdapter: CitasAdapter
    private lateinit var citasViewModel: CitasViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireContext())
        val root = inflater.inflate(R.layout.fragment_citas, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendar=view.findViewById<CalendarView>(R.id.calendarView)
        val recyclerViewPersonas= view.findViewById<RecyclerView>(R.id.recycleViewCita)
        val fecha_actual= view.findViewById<TextView>(R.id.fecha_actual)
        fecha_actual.visibility=TextView.GONE
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val selectedDate = sdf.format(Date(year - 1900, month, dayOfMonth))
                val databaseAccess = DatabaseAccess.getInstance(this.requireContext())
                databaseAccess!!.open()
                val citas = databaseAccess!!.cita(selectedDate, user)
                databaseAccess!!.close()

                val personaList: MutableList<CitasViewModel> = mutableListOf<CitasViewModel>()
                for(i in 0 until (citas.size) step 3){
                    personaList.add(
                        CitasViewModel(
                            citas[i], citas[i+1], citas[i+2])
                    )
                }
                if(citas.isEmpty()){
                    recyclerViewPersonas.visibility= RecyclerView.GONE
                    Toast.makeText(this.requireContext(), "No existen citas programadas", Toast.LENGTH_SHORT).show()
                } else {
                    recyclerViewPersonas.visibility= RecyclerView.VISIBLE
                    personaAdapter = CitasAdapter(requireActivity().applicationContext, personaList)
                    recyclerViewPersonas.adapter = personaAdapter
                    recyclerViewPersonas.layoutManager = LinearLayoutManager(this.requireContext())
                    recyclerViewPersonas.smoothScrollToPosition(personaList.size)
                }
            }
        }


    }


    /*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fecha_cita=view.findViewById<TextView>(R.id.fecha_actual)
        val hora_cita=view.findViewById<TextView>(R.id.hora_cita)
        val lugar_cita=view.findViewById<TextView>(R.id.lugar_cita)
        val dir_cita=view.findViewById<TextView>(R.id.direccion)
        val ruta=view.findViewById<Button>(R.id.buscar_ruta)


        ruta.setOnClickListener {
            val geocoder = Geocoder(this.requireContext(), Locale.getDefault())
            val addresses: List<Address> = geocoder.getFromLocationName(dir_cita.text as String?, 1)
            val dir: Address = addresses[0]

            if (ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
               //Uf papa no tienes permisos
                Toast.makeText(this.requireContext(), "Debe dar permisos para acceder a su ubicación", Toast.LENGTH_SHORT).show()
            }
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        val distance : Float
                        val place_location = Location("")
                        place_location.latitude = dir.latitude
                        place_location.longitude = dir.longitude
                        distance = place_location.distanceTo(location)
                        Toast.makeText(this.requireContext(),
                                "Distancia entre tu localización y "+lugar_cita.text+" es de "+distance.roundToInt().toString()+" metros", Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(this.requireContext(), "Debe activar su ubicación", Toast.LENGTH_SHORT).show()
                    }
                }

        }


    }*/

}