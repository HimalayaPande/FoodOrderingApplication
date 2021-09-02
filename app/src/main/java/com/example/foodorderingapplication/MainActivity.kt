package com.example.foodorderingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val image = findViewById<ImageView>(R.id.gifImageView)
        Glide.with(this).load(R.drawable.fooddelivery).into(image)
        supportActionBar?.hide()

        // access the items of the list
        val languages = resources.getStringArray(R.array.Languages)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, languages)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {

                    when(languages[position]){
                        "Admin" -> {
                            val i = Intent(this@MainActivity, AdminLoginActivity::class.java)
                            startActivity(i)
                        }
                        "Restaurant Owner" ->{
                            val i = Intent(this@MainActivity, RestaurantLoginActivity::class.java)
                            startActivity(i)
                        }
                        "Customer" ->{
                            val i = Intent(this@MainActivity, CustomerLoginActivity::class.java)
                            startActivity(i)
                        }
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}