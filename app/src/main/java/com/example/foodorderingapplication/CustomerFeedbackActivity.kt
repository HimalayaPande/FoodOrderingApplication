package com.example.foodorderingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CustomerFeedbackActivity : AppCompatActivity() {
    lateinit var rating1: RatingBar
    lateinit var submit1: Button
    lateinit var comment: EditText
    private var database = FirebaseDatabase.getInstance()
    var auth: FirebaseAuth= FirebaseAuth.getInstance()
    val userid=auth.currentUser!!.uid
    private var myorders_database1: DatabaseReference? = database.getReference().child("AllOrders")
    private var myorders_database: DatabaseReference? = database.getReference().child("FeedbackTable")
    var data2 = HashMap<String,String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_feedback)
        supportActionBar?.setTitle(getString(R.string.alfa))
        var itemlist= intent.getStringExtra("items_list")
        var totalcost= intent.getStringExtra("total_cost")
        var orderedDate= intent.getStringExtra("orderedDate")
        var restname= intent.getStringExtra("rest_name")
        var restid= intent.getStringExtra("rest_id")
        var orderid =intent.getStringExtra("order_id")
        rating1=findViewById(R.id.ratingbar)
        submit1=findViewById(R.id.submit1)
        comment=findViewById(R.id.comments)
        myorders_database1?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(items in snapshot.children){
                    if(orderid.toString()==items.child("order_id").getValue().toString()){
                        items.child("rate").ref.setValue("Rated")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        submit1.setOnClickListener {
            data2.put("items_list",itemlist.toString())
            data2.put("totalcost",totalcost.toString())
            data2.put("orderedDate",orderedDate.toString())
            data2.put("rest_name",restname.toString())
            data2.put("rest_id",restid.toString())
            data2.put("rating",rating1.rating.toString())
            data2.put("comments",comment.text.toString())
            myorders_database?.push()?.setValue(data2)
            Toast.makeText(this,"Feedback Submitted Successfully",Toast.LENGTH_LONG).show()
            startActivity(Intent(this, CustomerDashboard::class.java))
        }

    }
}