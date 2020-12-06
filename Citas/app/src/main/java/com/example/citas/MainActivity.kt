package com.example.citas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.citas.ui.home.HomeFragment
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_prueba2.*
import kotlinx.android.synthetic.main.activity_prueba2.tv_nombre
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    var nombre = ""
    var correo = ""
    var photo = ""
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = nav_view as NavigationView
        val hView: View = navigationView.getHeaderView(0)
        val nav_user = hView.tv_nombrenav as TextView
        val nav_emai = hView.tv_navemail as TextView
        val nav_photo = hView.navImage as ImageView


        //https://lh3.googleusercontent.com_a/AOh14GjX3VYury3eCfzViOAGOyewZWL9e_gEhXa_xFXLQ



        val bundles = intent.extras
        Log.d("gg", "perro")
        if (bundles != null) {
            nombre = bundles.getString("name").toString()
            correo = bundles.getString("email").toString()
            photo = bundles.getString("photo").toString()
            Toast.makeText(applicationContext, photo, Toast.LENGTH_LONG).show()
            Log.d("gg", "perro: $nombre")
            nav_emai.setText(nombre)
            nav_user.setText(correo)
            Picasso.get().load(photo).resize(250, 250).centerCrop().into(nav_photo)
            //nav_photo.setImageURI(Uri.parse("https://lh3.googleusercontent.com_a/AOh14GjX3VYury3eCfzViOAGOyewZWL9e_gEhXa_xFXLQ"))

        }

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.busquedaNegociosFragment,
                R.id.nav_notificaciones,
                R.id.nav_citas,
                R.id.nav_negocios,
                R.id.informacionNegociosFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
