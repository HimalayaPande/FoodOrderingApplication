package com.example.foodorderingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.foodorderingapplication.Models.MyProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CustomerSignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var database = FirebaseDatabase.getInstance()
    private var myProfile_databaseReference: DatabaseReference? = database.getReference().child("MyProfile")
    var data = ArrayList<MyProfile>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_sign_up)
        if(supportActionBar!=null){
            supportActionBar!!.hide()
        }
        val signup: Button = findViewById(R.id.btnRegister)
        val alreadyHaveAccount = findViewById<TextView>(R.id.alreadyHaveAccount)
        auth = FirebaseAuth.getInstance()
        signup.setOnClickListener {
            signupUser()
        }
        alreadyHaveAccount.setOnClickListener {
            startActivity(Intent(this, CustomerLoginActivity::class.java))
        }
    }
    fun signupUser(){
        val name = findViewById<EditText>(R.id.inputUsername)
        val email = findViewById<EditText>(R.id.inputEmail)
        val phnNo = findViewById<EditText>(R.id.phnNo)
        val cityname =findViewById<EditText>(R.id.customercityname)
        val pwd = findViewById<EditText>(R.id.inputPassword)
        val repwd = findViewById<EditText>(R.id.inputConformPassword)
        if(email.text.isEmpty()){
            name.error= "Please enter the email"
            name.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            name.error= "Please enter Valid Mail Id"
            name.requestFocus()
            return
        }
        if(phnNo.text.isEmpty()){
            name.error= "Please enter the Phone Number"
            name.requestFocus()
            return
        }
        if(!Patterns.PHONE.matcher(phnNo.text.toString()).matches()){
            name.error= "Please enter Valid Phone Number"
            name.requestFocus()
            return
        }
        if(name.text.isEmpty()){
            name.error= "Please enter the name"
            name.requestFocus()
            return
        }
        if(cityname.text.isEmpty()){
            cityname.error= "Please enter the city name"
            return
        }
        if(pwd.text.isEmpty()){
            name.error= "Please enter the Password"
            name.requestFocus()
            return
        }
        if(repwd.text.isEmpty()){
            repwd.error= "Please Retype the Password"
            repwd.requestFocus()
            return
        }
        if(!(pwd.text.toString().equals(repwd.text.toString()))){
            repwd.error ="Password not matched"
            repwd.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(email.text.toString(), pwd.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    var userId = auth.currentUser?.uid
                    myProfile_databaseReference = myProfile_databaseReference?.child(userId!!)
                    addUserDetails(name.text.toString(),email.text.toString(),phnNo.text.toString(),cityname.text.toString())
                    startActivity(Intent(this, LocationActivity::class.java))
                    finish()

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }
    fun addUserDetails(name:String, email:String, phnNo:String,cityname: String){

        myProfile_databaseReference?.child("username")?.setValue(name)
        myProfile_databaseReference?.child("useremail")?.setValue(email)
        myProfile_databaseReference?.child("userMobileNumber")?.setValue(phnNo)
        myProfile_databaseReference?.child("usercityname")?.setValue(cityname)
    }
}