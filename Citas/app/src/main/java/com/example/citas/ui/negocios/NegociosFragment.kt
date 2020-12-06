package com.example.citas.ui.negocios

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.citas.R
import com.example.citas.ui.DatabaseAccess
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class NegociosFragment : Fragment() {

    private lateinit var negociosViewModel: NegociosViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        negociosViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(NegociosViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireContext())
        val root = inflater.inflate(R.layout.fragment_negocios, container, false)
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val search=view.findViewById<Button>(R.id.button_buscar_negocios)
        val name_search=view.findViewById<EditText>(R.id.editTextTextPersonName)
        val location=view.findViewById<EditText>(R.id.dondeEstaslabel)
        val self_location= view.findViewById<CheckBox>(R.id.checkBox)
        val max_distance= view.findViewById<SeekBar>(R.id.seekBar)
        max_distance.progress = 1
        search.setOnClickListener{
            val max_meters=max_distance.progress *500
            var input_location = Location("")
            if(self_location.isChecked) {
                if (ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this.requireContext(), "Debe dar permisos para acceder a su ubicación", Toast.LENGTH_SHORT).show()
                }
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        input_location.longitude=location.longitude
                        input_location.latitude=location.latitude
                        make_search(name_search, input_location, max_meters)

                    }else {
                        Toast.makeText(this.requireContext(), "Debe activar su ubicación", Toast.LENGTH_SHORT).show()
                    }
                }
            } else{
                input_location=getLocationFromAddress(location.text.toString())
                make_search(name_search, input_location, max_meters)
            }
        }
    }

    fun make_search(name_search : TextView, input_location: Location, max_meters : Int) {
        val databaseAccess = DatabaseAccess.getInstance(this.requireContext())
        databaseAccess!!.open()
        val sites = databaseAccess!!.makeSearch(name_search.text.toString())
        databaseAccess!!.close()

        //Toast.makeText(this.requireContext(), "Max meters $max_meters", Toast.LENGTH_SHORT).show()

        val locales: MutableList<String> = ArrayList()
        for(i in 0 until (sites.size) step 2){
            //Toast.makeText(this.requireContext(), distance_between_two_locations(input_location, getLocationFromAddress(sites[i+1])).toString(), Toast.LENGTH_SHORT).show()
            val distance=distance_between_two_locations(input_location, getLocationFromAddress(sites[i+1]))
            if(distance<=max_meters){
                locales.add(sites[i])
                locales.add(distance.toString())
            }
        }
        if(locales.isEmpty()){
            Toast.makeText(this.requireContext(), "No se encontraron coincidencias", Toast.LENGTH_SHORT).show()
        }else {
            val data = Bundle()
            data.putStringArray("data", locales.toTypedArray())
            findNavController().navigate(R.id.action_nav_negocios_to_busquedaNegociosFragment, data)
        }


        /*
        for(i in 0 until (locales.size)){
            Toast.makeText(this.requireContext(), locales[i], Toast.LENGTH_SHORT).show()
        }
         */

    }

    fun distance_between_two_locations(l1 : Location, l2: Location) : Int {
        return l1.distanceTo(l2).toInt()
    }
    fun getLocationFromAddress(address: String): Location {
        val geocoder = Geocoder(this.requireContext(), Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocationName(address, 1)
        val dir: Address = addresses[0]
        val place_location = Location("")
        place_location.latitude = dir.latitude
        place_location.longitude = dir.longitude
        return place_location
    }


}