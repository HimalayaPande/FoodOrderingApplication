package com.example.foodorderingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RestaurantLoginActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var login1: Button
    lateinit var email2: EditText
    lateinit var pwd: EditText
    lateinit var rest_id1: String
    lateinit var register1: TextView
    lateinit var signup1: TextView
    val restaurantDb = Firebase.database.getReference("RestaurantTable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_login)
        if(supportActionBar!=null){
            supportActionBar!!.hide()
        }
        register1=findViewById(R.id.alreadyaccount)
        signup1=findViewById(R.id.alreadyRegistered)
        mAuth = FirebaseAuth.getInstance()
        login1=findViewById(R.id.login1)
        login1.setOnClickListener {
            doLogin()
        }
        register1.setOnClickListener {
            startActivity(Intent(this, RestaurantRegistration::class.java))
        }
        signup1.setOnClickListener {
            startActivity(Intent(this, RestaurantSignupActivity::class.java))
        }
    }

    private fun doLogin(){
        email2 = findViewById<EditText>(R.id.email2)
        pwd = findViewById<EditText>(R.id.password2)
        if(email2.text.isEmpty()){
            email2.error= "Please enter the email"
            email2.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email2.text.toString()).matches()){
            email2.error= "Please enter Valid Mail Id"
            email2.requestFocus()
            return
        }
        if(pwd.text.isEmpty()){
            pwd.error= "Please enter the password"
            pwd.requestFocus()
            return
        }
        emailcheck()
        mAuth.signInWithEmailAndPassword(email2.text.toString(), pwd.text.toString())
            .addOnCompleteListener(this, OnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    if(user!=null) {
                        finish()
                        val intent=Intent(this, AddRestaurantDataActivity::class.java)
                        intent.putExtra("restidno",rest_id1)
                        startActivity(intent)
                        updateUI(user)
                    }
                }else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Login failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            })
    }

    private fun emailcheck() {
        restaurantDb.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(items in snapshot.children){
                    //Toast.makeText(this@RestaurantLoginActivity,items.child("email").getValue().toString(),Toast.LENGTH_LONG).show()
                    //Toast.makeText(this@RestaurantLoginActivity,email2.text.toString(),Toast.LENGTH_LONG).show()
                    if(items.child("email").getValue()==(email2.text.toString())){
                        rest_id1=items.child("restid").getValue().toString()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = mAuth.currentUser
//        if(currentUser != null){
//            updateUI(currentUser)
//        }
//    }
    private fun updateUI(currentUser: FirebaseUser?){
        if(currentUser!=null){
            Toast.makeText(this,"login successful",Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(this,"Login Failed",Toast.LENGTH_LONG).show()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}