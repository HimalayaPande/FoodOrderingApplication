package com.example.foodorderingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapplication.Adapters.ViewCustomerDetailsAdapter
import com.example.foodorderingapplication.Models.ViewCustomerDetailsModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewCustomerDetailsActivity : AppCompatActivity() {
    private var database = FirebaseDatabase.getInstance()
    private  var data =ArrayList<ViewCustomerDetailsModel>()
    private var root = database.getReference().child("MyProfile")
    lateinit var adapt: ViewCustomerDetailsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_customer_details)
        supportActionBar?.setTitle("Alfa Food Service")
        val recycler1= findViewById<RecyclerView>(R.id.RecyclerViewCustomers)
        root.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(items in snapshot.children){
                    data.add(ViewCustomerDetailsModel((items.child("username").getValue().toString()),items.child("useremail").getValue().toString(),items.child("userMobileNumber").getValue().toString(),items.child("usercityname").getValue().toString()))
                }
                adapt.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        recycler1.layoutManager= LinearLayoutManager(this)
        recycler1.setHasFixedSize(true)
        adapt = ViewCustomerDetailsAdapter(this,data)
        recycler1.adapter = adapt
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, AdminDashboardActivity::class.java))
        finish()
    }

}