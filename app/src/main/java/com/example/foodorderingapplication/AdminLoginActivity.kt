package com.example.foodorderingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class AdminLoginActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var login:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)
        if(supportActionBar!=null){
            supportActionBar!!.hide()
        }
        mAuth = FirebaseAuth.getInstance()
        login=findViewById(R.id.login)
        login.setOnClickListener {
            loginuser()
        }
    }
    private fun loginuser() {
        email = findViewById<EditText>(R.id.email)
        password = findViewById<EditText>(R.id.password)
        if (email.text.isEmpty()) {
            email.error = "Please enter the email"
            email.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
            email.error = "Please enter Valid Mail Id"
            email.requestFocus()
            return
        }
        if(!email.text.contains("@alfa.org.in")){
            email.error = "Please enter the correct email"
            email.requestFocus()
            return
        }
        if (password.text.isEmpty()) {
            password.error = "Please enter the password"
            password.requestFocus()
            return
        }
        mAuth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    if (user != null) {
                        updateUI(user)
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Login Failed ",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        if(currentUser != null){
            updateUI(currentUser)
        }

    }
    private fun updateUI(currentUser: FirebaseUser?){
        if(currentUser!=null){
            startActivity(Intent(this, AdminDashboardActivity::class.java))
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