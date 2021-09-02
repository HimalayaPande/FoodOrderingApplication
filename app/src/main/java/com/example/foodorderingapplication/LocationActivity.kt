package com.example.foodorderingapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.IOException
import java.util.*

class LocationActivity : AppCompatActivity() {
    private var database = FirebaseDatabase.getInstance()
    private var myLoc_database: DatabaseReference? = database.getReference().child("MyLocation")
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit  var textView1: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        supportActionBar?.setTitle(getString(R.string.alfa))
        val image = findViewById<ImageView>(R.id.loc_gif)
        var orderNow = findViewById<Button>(R.id.order_now)
        Glide.with(this).load(R.drawable.tenor).into(image)
        textView1 = findViewById(R.id.loc_address)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                44
            )
        }
        orderNow.setOnClickListener {
            val intent = Intent(this, CustomerDashboard::class.java)
            startActivity(intent)


        }

    }
    @SuppressLint("MissingPermission")
    fun getLocation(){
        lateinit var address:String
        lateinit var city:String
        val hp = HashMap<String,String>()
        fusedLocationProviderClient.lastLocation
            .addOnCompleteListener { task ->
                val location = task.result
                if (location != null) {
                    val geocoder =
                        Geocoder(this, Locale.getDefault())
                    try {
                        val addresses =
                            geocoder.getFromLocation(
                                location.latitude,
                                location.longitude,
                                1
                            )
                        textView1.text = Html.fromHtml(
                            "<font color='#6200EE'><br></font>" + addresses[0].getAddressLine(
                                0
                            )

                        )
                        address = addresses[0].getAddressLine(0)
                        city = addresses[0].locality
                        hp.put("address",address)
                        myLoc_database?.push()?.setValue(hp)

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
    }
}