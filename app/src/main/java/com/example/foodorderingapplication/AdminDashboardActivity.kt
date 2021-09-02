package com.example.foodorderingapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapplication.Adapters.AdminApprovalAdapter
import com.example.foodorderingapplication.Interfaces.OnClickInterface
import com.example.foodorderingapplication.Models.AdminApprovalDataModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminDashboardActivity : AppCompatActivity() , OnClickInterface {
    private lateinit var mAuth: FirebaseAuth
    var data= ArrayList<AdminApprovalDataModel>()

    lateinit var adapter: AdminApprovalAdapter
    private var database = FirebaseDatabase.getInstance()
    private var admindb = database.getReference().child("AdminApprovalTable")
    lateinit var recycler:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)
        supportActionBar?.setTitle("Alfa Food Service")
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.viewrestaurantdetails -> {
                startActivity(Intent(applicationContext, ApprovedRestaurantDataActivity::class.java))
                finish()
            }
                R.id.viewcustomerdetails -> {
                startActivity(Intent(applicationContext, ViewCustomerDetailsActivity::class.java))
                    finish()
            }
                R.id.viewcustomerfeedback -> {
                    startActivity(Intent(applicationContext, AdminFeedbackActivity::class.java))
                   finish()
                }
            }
            true
        })
        mAuth = FirebaseAuth.getInstance()
        recycler=findViewById(R.id.recycler_view)
        admindb.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                for (value in snapshot.children){
                    data.add(
                        AdminApprovalDataModel(
                            value.child("restaurantimage").getValue().toString(),
                            value.child("restaurantname").getValue().toString(),
                            value.child("cityname").getValue().toString(),
                            value.child("rating").getValue().toString(),
                            value.child("cost").getValue().toString(),
                            value.child("restid").getValue().toString(),
                            value.child("time").getValue().toString(),
                            value.child("email").getValue().toString(),
                            value.child("phonenumber").getValue().toString(),
                            value.child("status").getValue().toString().toBoolean()

                        )
                    )
                }
                adapter.notifyDataSetChanged()


            }

        })
        recycler.layoutManager= LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
        adapter= AdminApprovalAdapter(this, data,this)
        recycler.adapter=adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.signout ->{
                mAuth.signOut()
                finishAffinity()
                startActivity(Intent(this, AdminLoginActivity::class.java))
                true
            }
            else -> return false
        }

    }



    override fun onClick(name: Int) {
        val intent=Intent(this, DisplayRestaurantDataActivity::class.java)
        intent.putExtra("name",data[name].name)
        intent.putExtra("city",data[name].CityName)
        intent.putExtra("ph",data[name].phonenumber)
        intent.putExtra("email",data[name].email)
        intent.putExtra("cost",data[name].cost)
        intent.putExtra("time",data[name].time)
        intent.putExtra("rating",data[name].rating)
        intent.putExtra("status",data[name].status.toString())
        intent.putExtra("id",data[name].res_id)
        startActivity(intent)
        finish()


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