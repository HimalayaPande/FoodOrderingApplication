package com.example.foodorderingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapplication.Adapters.MyOrdersAdapter
import com.example.foodorderingapplication.Interfaces.OnClickInterface
import com.example.foodorderingapplication.Models.OrderHistoryModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class OrderHistory : AppCompatActivity() , OnClickInterface {
    lateinit var adapt: MyOrdersAdapter
    private var database = FirebaseDatabase.getInstance()
    var auth: FirebaseAuth= FirebaseAuth.getInstance()
    val userid=auth.currentUser!!.uid
    private var myorders_database: DatabaseReference? = database.getReference().child("AllOrders")

    var data1= ArrayList<OrderHistoryModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)
        supportActionBar?.setTitle(getString(R.string.alfa))
        myorders_database?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(data in snapshot.children) {
                    if (data.child("user_key").getValue().toString() == userid.toString()) {
                        data1.add(OrderHistoryModel(data.child("items_list").getValue().toString(), data.child("total_cost").getValue().toString(), data.child("rest_name").getValue().toString(), data.child("rest_id").getValue().toString(), data.child("order_date").getValue().toString(), data.child("rate").getValue().toString(), data.child("order_id").getValue().toString(), data.child("user_key").getValue().toString()))
                    }
                }
                adapt.notifyDataSetChanged()
            }

        })
        val recyclervieww= findViewById<RecyclerView>(R.id.order_history)
        recyclervieww.layoutManager= LinearLayoutManager(this)
        recyclervieww.setHasFixedSize(true)
        adapt = MyOrdersAdapter(this,data1,this)
        recyclervieww.adapter = adapt

    }

    override fun onClick(name: Int) {
        val intent=Intent(this, CustomerFeedbackActivity::class.java)
        intent.putExtra("items_list",data1[name].itemlist)
        intent.putExtra("total_cost",data1[name].totalcost)
        intent.putExtra("orderedDate",data1[name].orderedDate)
        intent.putExtra("rest_name",data1[name].rest_name)
        intent.putExtra("rest_id",data1[name].rest_id)
        intent.putExtra("order_id",data1[name].orderid)
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

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, CustomerDashboard::class.java))
        finish()
    }
}