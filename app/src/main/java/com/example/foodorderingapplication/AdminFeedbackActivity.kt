package com.example.foodorderingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapplication.Adapters.RestaurantFeedbackAdapter
import com.example.foodorderingapplication.Models.RestaurantFeedbackModel
import com.google.firebase.database.*

class AdminFeedbackActivity : AppCompatActivity() {
    lateinit var adapt: RestaurantFeedbackAdapter
    private var database = FirebaseDatabase.getInstance()
    private var feedback_database: DatabaseReference? = database.getReference().child("FeedbackTable")
    var data2 =ArrayList<RestaurantFeedbackModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_feedback)
        supportActionBar?.setTitle("Alfa Food Service")
        val recyclervieww= findViewById<RecyclerView>(R.id.adminfeedback)
        recyclervieww.layoutManager= LinearLayoutManager(this)
        recyclervieww.setHasFixedSize(true)
        feedback_database?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(items in snapshot.children){
                    if(items.child("comments").getValue().toString()!=""){
                        data2.add(RestaurantFeedbackModel(items.child("rest_id").getValue().toString(),items.child("rest_name").getValue().toString(),items.child("rating").getValue().toString(),items.child("totalcost").getValue().toString(),items.child("comments").getValue().toString(),items.child("items_list").getValue().toString()))
                    }
                    else{
                        data2.add(RestaurantFeedbackModel(items.child("rest_id").getValue().toString(),items.child("rest_name").getValue().toString(),items.child("rating").getValue().toString(),items.child("totalcost").getValue().toString(),"No Comments",items.child("items_list").getValue().toString()))
                    }
                }
                adapt.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        adapt = RestaurantFeedbackAdapter(this,data2)
        recyclervieww.adapter = adapt
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, AdminDashboardActivity::class.java))
        finish()
    }

}