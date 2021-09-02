package com.example.foodorderingapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.HashMap

@Suppress("UNREACHABLE_CODE")
class DisplayRestaurantDataActivity : AppCompatActivity() {
    lateinit var restname: TextView
    lateinit var restcity:TextView
    lateinit var restphno: TextView
    lateinit var restemail: TextView
    lateinit var restcost: TextView
    lateinit var resttime: TextView
    lateinit var rating: TextView
    lateinit var restid: TextView
    lateinit var register: Button
    lateinit var back:Button
    lateinit var key: String
    var hashMap = HashMap<String,String>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_restaurant_data)
        supportActionBar?.setTitle("Alfa Food Service")
        val obj= AdminDashboardActivity()
        back=findViewById(R.id.back)
        back.setOnClickListener {
            startActivity(Intent(this, AdminDashboardActivity::class.java))
            finish()
        }


        val database = FirebaseDatabase.getInstance()
        val admindb = database.getReference().child("AdminApprovalTable")
        var resttable=database.getReference().child("RestaurantTable")
        restname=findViewById(R.id.name)
        restid=findViewById(R.id.id)
        restcity=findViewById(R.id.city)
        restphno=findViewById(R.id.phone)
        restemail=findViewById(R.id.email)
        restcost=findViewById(R.id.cost)
        resttime=findViewById(R.id.time)
        rating=findViewById(R.id.rating)
        register=findViewById(R.id.approve)

     restname.text=    getString(R.string.restaurant_name)+intent.getStringExtra("name")
        restcity.text= getString(R.string.restaurant_city) +intent.getStringExtra("city")
        restphno.text= getString(R.string.phone_number)+intent.getStringExtra("ph")
        restemail.text=getString(R.string.email_id)+intent.getStringExtra("email")
        restcost.text= getString(R.string.avg_for_two)+intent.getStringExtra("cost")
        resttime.text= getString(R.string.avg_delivery_time)+intent.getStringExtra("time")
        rating.text=   getString(R.string.rating1)+intent.getStringExtra("rating")
        restid.text=   getString(R.string.restaurant__id)+intent.getStringExtra("id")?.toInt().toString()
        var restid1 = intent.getStringExtra("id")?.toInt().toString()

register.setOnClickListener {
    register.text=getString(R.string.Approved)
    admindb.addValueEventListener(object :ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            for(items in snapshot.children)
            {
                if(items.child("restid").getValue()==restid1)
                {
                 key= items.key!!
                    hashMap.put("restaurantname",items.child("restaurantname").getValue().toString())
                    hashMap.put("cityname",items.child("cityname").getValue().toString())
                    hashMap.put("rating",items.child("rating").getValue().toString())
                    hashMap.put("cost",items.child("cost").getValue().toString())
                    hashMap.put("restid",items.child("restid").getValue().toString())
                    hashMap.put("time",items.child("time").getValue().toString())
                    hashMap.put("email",items.child("email").getValue().toString())
                    hashMap.put("phonenumber",items.child("phonenumber").getValue().toString())
                    hashMap.put("restaurantimage",items.child("restaurantimage").getValue().toString())
                    hashMap.put("status","True")
                    resttable.push().setValue(hashMap)
                    admindb.child(key).removeValue()
                    return
                }
            }

        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    })
}

    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, AdminDashboardActivity::class.java))
        finish()
    }
}