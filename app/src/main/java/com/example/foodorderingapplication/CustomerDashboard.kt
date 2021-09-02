package com.example.foodorderingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapplication.Adapters.Adapter3
import com.example.foodorderingapplication.Adapters.DashBoardoffersAdapter
import com.example.foodorderingapplication.Interfaces.OnClickInterface
import com.example.foodorderingapplication.Models.Image
import com.example.foodorderingapplication.Models.RestaurantDataModel1
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CustomerDashboard : AppCompatActivity() , OnClickInterface {
    private lateinit var auth: FirebaseAuth
    var data= ArrayList<RestaurantDataModel1>()
    lateinit var adapt: Adapter3
    var rImage: ImageView? = null
    lateinit var city: String
    private var database = FirebaseDatabase.getInstance()
    private var root = database.getReference().child("RestaurantTable")
    private var myLoc_database: DatabaseReference? = database.getReference().child("MyLocation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_dashboard)
        supportActionBar?.setTitle(getString(R.string.alfa))
        auth= FirebaseAuth.getInstance()
        val loc_address = findViewById<TextView>(R.id.edittext1)

        myLoc_database?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(item in snapshot.children){

                    loc_address.text = item.child("address").getValue().toString()
                }
            }

        })
        root.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (value in snapshot.children){
                    data.add(
                            RestaurantDataModel1(
                                    value.child("restaurantimage").getValue().toString(),
                                    value.child("restaurantname").getValue().toString(),
                                    value.child("cityname").getValue().toString(),
                                    R.drawable.ic_star,
                                    value.child("rating").getValue().toString(),
                                    "₹ "+value.child("cost").getValue().toString()+" per two person",
                                    value.child("restid").getValue().toString(),
                                    value.child("time").getValue().toString()
                            )
                    )
                }
                adapt.notifyDataSetChanged()
            }
        })
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.cart -> {
                    startActivity(Intent(applicationContext, OrderHistory::class.java))
                    finish()
                }
                R.id.account -> {
                    startActivity(Intent(applicationContext, Profile::class.java))
                    finish()
                }
            }
            true
        })
        val imagess= listOf<Image>(
                Image(R.drawable.five),
                Image(R.drawable.six),
                Image(R.drawable.onee),
                Image(R.drawable.two),
                Image(R.drawable.three),
                Image(R.drawable.four)
                )
        val recycler= findViewById<RecyclerView>(R.id.recyclerview)
        val HLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        recycler.layoutManager= LinearLayoutManager(this)
        recycler.layoutManager=HLinearLayoutManager
        recycler.setHasFixedSize(true)
        recycler.adapter=
                DashBoardoffersAdapter(this, imagess)
        val recycler1= findViewById<RecyclerView>(R.id.recyclerviewactivity)
        recycler1.layoutManager= LinearLayoutManager(this)
        recycler1.setHasFixedSize(true)
        adapt = Adapter3(this,data,this)
        recycler1.adapter = adapt
    }
    override fun onClick(position: Int) {
        val intent=Intent(Intent(this, MenuItemsActivity::class.java))
        intent.putExtra("restname",data[position].name)
        intent.putExtra("restrating",(data[position].rating).toString())
        intent.putExtra("cost",data[position].cost)
        intent.putExtra("res_id",(data[position].res_id).toString())
        intent.putExtra("rest_dist",data[position].time)
        intent.putExtra("rest_location",data[position].CityName)
        startActivity(intent)
    }

    override fun onAdd(addItemIndex: Int) {
        TODO("Not yet implemented")
    }

    override fun addQuantity(position: Int, quantity: String) {
        TODO("Not yet implemented")
    }

    override fun decreaseQuantity(position: Int, quantity: String) {
        TODO("Not yet implemented")
    }

}