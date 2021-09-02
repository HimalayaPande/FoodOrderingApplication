package com.example.foodorderingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapplication.Adapters.ApprovedRestaurantAdapter
import com.example.foodorderingapplication.Models.ApprovedRestaurantDataModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ApprovedRestaurantDataActivity : AppCompatActivity() {
    var data= ArrayList<ApprovedRestaurantDataModel>()
    lateinit var adapt: ApprovedRestaurantAdapter
    private var database = FirebaseDatabase.getInstance()
    private var restaurantdb = database.getReference().child("RestaurantTable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approved_restaurant_data)
        supportActionBar?.setTitle("Alfa Food Service")
        restaurantdb.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (value in snapshot.children){
                    data.add(
                            ApprovedRestaurantDataModel(
                                    value.child("restaurantimage").getValue().toString(),
                                    value.child("restaurantname").getValue().toString(),
                                    value.child("cityname").getValue().toString(),
                                    R.drawable.ic_star,
                                    value.child("rating").getValue().toString(),
                                "₹ "+value.child("cost").getValue().toString()+" per two person",
                                    value.child("restid").getValue().toString().toInt(),
                                    value.child("time").getValue().toString()
                            )
                    )
                }
                adapt.notifyDataSetChanged()
            }

        })
        val recycler1= findViewById<RecyclerView>(R.id.recyclerviewactivity)
        recycler1.layoutManager= LinearLayoutManager(this)
        recycler1.setHasFixedSize(true)
        adapt = ApprovedRestaurantAdapter(this,data)
        recycler1.adapter = adapt
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, AdminDashboardActivity::class.java))
        finish()
    }
}