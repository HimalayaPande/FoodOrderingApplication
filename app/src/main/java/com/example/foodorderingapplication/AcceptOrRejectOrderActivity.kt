package com.example.foodorderingapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapplication.Interfaces.AcceptOrRejectInterface
import com.example.foodorderingapplication.Adapters.AcceptOrRejectAdapter
import com.example.foodorderingapplication.Models.KeyModel
import com.example.foodorderingapplication.Models.AcceptOrRejectModel
import com.google.firebase.database.*
import java.util.ArrayList

class AcceptOrRejectOrderActivity : AppCompatActivity() , AcceptOrRejectInterface {
    private var database = FirebaseDatabase.getInstance()
    private var hashMap = HashMap<String,String>()
    private lateinit var adapt : AcceptOrRejectAdapter
    var keylist = ArrayList<KeyModel>()
    private var dummy_database: DatabaseReference? = database.getReference().child("DummyOrders")
    private var allorders_database: DatabaseReference? = database.getReference().child("AllOrders")
    var data3 = ArrayList<AcceptOrRejectModel>()
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accept_or_reject_order)
        supportActionBar?.setTitle("Alfa Food Service")
        recyclerView=findViewById(R.id.acceptorreject)
        val rest_id1=intent.getStringExtra("restid5").toString()
        dummy_database?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(data1 in snapshot.children) {
                    if (rest_id1 == data1.child("rest_id").getValue().toString()) {
                        data3.add(AcceptOrRejectModel(data1.child("items_list").getValue().toString(), data1.child("total_cost").getValue().toString(), data1.child("rest_name").getValue().toString(), data1.child("rest_id").getValue().toString(), data1.child("order_date").getValue().toString(), data1.child("rate").getValue().toString(), data1.child("order_id").getValue().toString(), data1.child("user_key").getValue().toString()))
                        keylist.add(KeyModel(data1.key!!))
                    }
                }
                adapt.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        val HLinearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.layoutManager=HLinearLayoutManager
        recyclerView.setHasFixedSize(true)
        adapt= AcceptOrRejectAdapter(this, data3,this)
        recyclerView.adapter=adapt
    }

    override fun OnAccept(position: Int) {
        Toast.makeText(this,"Order Accepted",Toast.LENGTH_LONG).show()
        val key = keylist[position].key
        dummy_database?.child(key)?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
//                for (data in snapshot.children) {
//                    hashMap.put("items_list", data.child("items_list").getValue().toString())
//                    hashMap.put("rest_name", data.child("rest_name").getValue().toString())
//                    hashMap.put("rest_loc", data.child("rest_loc").getValue().toString())
//                    hashMap.put("order_date", data.child("order_date").getValue().toString())
//                    hashMap.put("total_cost", data.child("total_cost").getValue().toString())
//                    hashMap.put("rest_id", data.child("rest_id").getValue().toString())
//                    hashMap.put("rate", data.child("rate").getValue().toString())
//                    hashMap.put("order_id", data.child("order_id").getValue().toString())
//                    hashMap.put("user_key", data.child("user_key").getValue().toString())
//                }
                allorders_database?.push()?.setValue(snapshot.getValue())
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        dummy_database?.child(key)?.removeValue()
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Order has been Accepted")
        builder.setPositiveButton("Ok"){
            obj, which -> startActivity(intent)
            finish()
        }
        val alert = builder.create()
        alert.setCancelable(false)
        alert.show()
    }
    override fun OnReject(position: Int) {
        Toast.makeText(this,"Order Rejected",Toast.LENGTH_LONG).show()
        val key = keylist[position].key
        dummy_database?.child(key)?.removeValue()
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Order has been Rejected")
        builder.setPositiveButton("Ok"){
            obj, which -> startActivity(intent)
            finish()
        }
        val alert = builder.create()
        alert.setCancelable(false)
        alert.show()

    }

}