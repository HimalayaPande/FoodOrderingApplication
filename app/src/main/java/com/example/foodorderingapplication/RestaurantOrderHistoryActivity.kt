package com.example.foodorderingapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapplication.Models.OrderHistoryModel
import com.example.foodorderingapplication.Adapters.RestaurantOrderhistoryAdapter
import com.google.firebase.database.*

class RestaurantOrderHistoryActivity : AppCompatActivity() {
    lateinit var adapt: RestaurantOrderhistoryAdapter
    private var database = FirebaseDatabase.getInstance()
    private var allorders_database: DatabaseReference? = database.getReference().child("AllOrders")

    var data1= ArrayList<OrderHistoryModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_order_history)
        supportActionBar?.setTitle("Alfa Food Service")
        val rest_id1=intent.getStringExtra("restid3").toString()
        allorders_database?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(data in snapshot.children) {
                    if (rest_id1 == data.child("rest_id").getValue()) {
                        data1.add(
                            OrderHistoryModel(
                                data.child("items_list").getValue().toString(),
                                data.child("total_cost").getValue().toString(),
                                data.child("rest_name").getValue().toString(),
                                data.child("rest_loc").getValue().toString(),
                                data.child("order_date").getValue().toString(),
                                data.child("rate").getValue().toString(),
                                data.child("order_id").getValue().toString(),
                                    data.child("user_key").getValue().toString()
                            )
                        )
                    }
                }
                adapt.notifyDataSetChanged()
            }

        })
        val recyclervieww= findViewById<RecyclerView>(R.id.order_history)
        recyclervieww.layoutManager= LinearLayoutManager(this)
        recyclervieww.setHasFixedSize(true)
        adapt = RestaurantOrderhistoryAdapter(this,data1)
        recyclervieww.adapter = adapt

    }

}