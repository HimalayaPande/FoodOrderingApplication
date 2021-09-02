package com.example.foodorderingapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapplication.Adapters.CartAdapter
import com.example.foodorderingapplication.Interfaces.OnClickInterface
import com.example.foodorderingapplication.Models.CartModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CartActivity : AppCompatActivity(), OnClickInterface {
    var orderid2:Int = 1
    lateinit var sharedPreferences: SharedPreferences
    private var database = FirebaseDatabase.getInstance()
    var auth: FirebaseAuth= FirebaseAuth.getInstance()
    private var myProfile_database: DatabaseReference? = database.getReference().child("MyProfile")
    private var myLoc_database: DatabaseReference? = database.getReference().child("MyLocation")
    private var order_database: DatabaseReference? = database.getReference().child("Cart")
//    private var myorders_database: DatabaseReference? = database.getReference().child("MyOrders").child(userid)
    private var allorders_database: DatabaseReference? = database.getReference().child("DummyOrders")
    lateinit var rest_name1:String
    lateinit var rest_loc1:String
    var items:String = ""
    var TOTAL_COST =0
    var order_no = 0
    var data1= ArrayList<CartModel>()
    lateinit var adapt: CartAdapter
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_activity_layout)
        supportActionBar?.setTitle(getString(R.string.alfa))
        TOTAL_COST = intent.getStringExtra("TOTAL_COST").toString().toInt()
        var TOTAL_QUANTITY = intent.getStringExtra("TOTAL_QUANTITY")
        val placeOrder = findViewById<Button>(R.id.placeOrder)
        val totalAmount = findViewById<TextView>(R.id.t_total)
        val youPay = findViewById<TextView>(R.id.t_grand_total)
        val userId = auth.currentUser!!.uid
        val user_name = findViewById<TextView>(R.id.profile_name)
        val user_email = findViewById<TextView>(R.id.profile_email)
        val user_mobileNo = findViewById<TextView>(R.id.profile_mobileNo)
        val address = findViewById<TextView>(R.id.profile_address)
        totalAmount.setText(TOTAL_COST.toString())
        youPay.setText(TOTAL_COST.toString())
        myProfile_database?.child(userId)?.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var name = snapshot.child("username").getValue()
                var email = snapshot.child("useremail").getValue()
                var mobileNum = snapshot.child("userMobileNumber").getValue()
                user_name.setText(name.toString())
                user_email.setText(email.toString())
                user_mobileNo.setText(mobileNum.toString())
            }

        })

        myLoc_database?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children) {

                    address.text = item.child("address").getValue().toString()
                }
            }
        })

        order_database?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(data in snapshot.children){
                    data1.add(CartModel(data.child("item_name").getValue().toString(),data.child("item_price").getValue().toString(),data.child("item_quantity").getValue().toString().toInt(),data.child("rest_name").getValue().toString(),data.child("rest_loc").getValue().toString(),data.child("category").getValue().toString(),data.child("res_id").getValue().toString()))
                }
                adapt.notifyDataSetChanged()
            }

        })
        val recyclervieww= findViewById<RecyclerView>(R.id.cart_list)
        recyclervieww.layoutManager= LinearLayoutManager(this)
        recyclervieww.setHasFixedSize(true)
        adapt = CartAdapter(this,data1,this)
        recyclervieww.adapter = adapt

        placeOrder.setOnClickListener {

            val hashMap = HashMap<String,String>()
            val currentDateTime = LocalDateTime.now()
            val date=currentDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
            val time=currentDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
            val date_time=date+" at "+ time

            for(item in data1){
                items += item.item_quantity.toString() + " X " +item.itemname.toString()+"\n"
                rest_name1 =  item.rest_name
                rest_loc1 = item.rest_loc
                hashMap.put("items_list",items)
                hashMap.put("rest_name",rest_name1)
                hashMap.put("rest_loc",rest_loc1)
                hashMap.put("order_date",date_time.toString())
                hashMap.put("total_cost",TOTAL_COST.toString())
                hashMap.put("rest_id",item.rest_id.toString())
                hashMap.put("rate","Rate Order")
                hashMap.put("order_id",orderid2.toString())
                hashMap.put("user_key",auth.currentUser!!.uid)
            }
            orderid2++;
            allorders_database?.push()?.setValue(hashMap)
//            myorders_database?.push()?.setValue(hashMap)
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)

        }

    }

    override fun onClick(name: Int) {
        TODO("Not yet implemented")
    }

    override fun onAdd(addItemIndex: Int) {
        TODO("Not yet implemented")
    }

    @SuppressLint("SetTextI18n")
    override fun addQuantity(position: Int, quantity:String) {
        val ToatlPrice = findViewById<TextView>(R.id.t_total)
        val youPay = findViewById<TextView>(R.id.t_grand_total)
        TOTAL_COST = TOTAL_COST.plus(data1[position].itemcost.toInt())
        ToatlPrice.setText(TOTAL_COST.toString())
        youPay.setText(TOTAL_COST.toString())
        data1[position].item_quantity  = quantity.toInt()

    }

    @SuppressLint("SetTextI18n")
    override fun decreaseQuantity(position: Int, quantity: String) {
        val youPay = findViewById<TextView>(R.id.t_grand_total)
        val ToatlPrice = findViewById<TextView>(R.id.t_total)
        if(quantity.toInt()<=0){ }
        else {
            TOTAL_COST = TOTAL_COST.minus(data1[position].itemcost.toInt())
            ToatlPrice.setText(TOTAL_COST.toString())
            youPay.setText(TOTAL_COST.toString())
        }
        data1[position].item_quantity  = quantity.toInt()
    }
    override fun onResume() {
        super.onResume()
        sharedPreferences = this.getSharedPreferences("OrderIdno2", Context.MODE_PRIVATE)
        orderid2 = sharedPreferences.getInt("idd2",1)
    }

    override fun onPause() {
        super.onPause()
        sharedPreferences = this.getSharedPreferences("OrderIdno2", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putInt("idd2", orderid2)
        editor.apply()
        editor.commit()
    }
}