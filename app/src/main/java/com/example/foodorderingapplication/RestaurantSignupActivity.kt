package com.example.foodorderingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RestaurantSignupActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    val restaurantDb = Firebase.database.getReference("RestaurantTable")
    lateinit var email1: EditText
    lateinit var password: EditText
    lateinit var confirmpassword: EditText
    lateinit var signup: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_sign_up)
        if(supportActionBar!=null){
            supportActionBar!!.hide()
        }
        email1 = findViewById(R.id.mail)
        password = findViewById(R.id.passwordd)
        confirmpassword = findViewById(R.id.cnfrmpassword)
        signup=findViewById(R.id.signup)
        mAuth = FirebaseAuth.getInstance()
        signup.setOnClickListener {
            restaurantDb.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var i=0
                    for(items in snapshot.children){
                        if(items.child("email").getValue()==email1.text.toString()){
                            i=1
                            if(confirmpassword.text.toString()==password.text.toString()){
                                mAuth.createUserWithEmailAndPassword(email1.text.toString(),password.text.toString())
                                    .addOnCompleteListener() { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(
                                                baseContext,
                                                "Registration successful",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            mAuth.signOut()
                                            val intent = Intent(
                                                this@RestaurantSignupActivity,
                                                RestaurantLoginActivity::class.java
                                            )
                                            //Toast.makeText(this@RestaurantSignupActivity,items.child("restid").getValue().toString(),Toast.LENGTH_LONG).show()
                                            startActivity(intent)
                                            finish()
                                        }
                                        else{
                                            Toast.makeText(baseContext,"Registration Unsuccessful",Toast.LENGTH_LONG).show()
                                        }
                                    }
                                    .addOnCanceledListener {
                                        Toast.makeText(baseContext,"Registration Unsuccessful",Toast.LENGTH_LONG).show()
                                    }
                            }
                            else{
                                confirmpassword.setError("Passwords not matched")
                            }
                        }
                    }
                    if(i==0){
                        email1.setError("Please enter the registered email")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        }
    }
}