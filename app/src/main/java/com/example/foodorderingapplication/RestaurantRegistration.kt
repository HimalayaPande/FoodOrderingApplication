package com.example.foodorderingapplication

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.foodorderingapplication.Models.RestaurantDataModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class RestaurantRegistration : AppCompatActivity() {
    var restidd1:Int = 1
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var storageReference: StorageReference
    var hashMap = HashMap<String,String>()
    lateinit var restaurantDataModel: RestaurantDataModel
    val adminDb = Firebase.database.getReference("AdminApprovalTable")
    lateinit var restname: TextInputEditText
    lateinit var restcity:TextInputEditText
    lateinit var restphno:TextInputEditText
    lateinit var restemail:TextInputEditText
    lateinit var restcost:TextInputEditText
    lateinit var resttime:TextInputEditText
    lateinit var register: Button
    lateinit var imageView: ImageView
    private lateinit var imageUrl: String
    private lateinit var filePath : Uri
    lateinit var upload: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restaurant_register)
        if(supportActionBar!=null){
            supportActionBar!!.hide()
        }
        restname=findViewById<TextInputEditText>(R.id.restname)
        restcity=findViewById<TextInputEditText>(R.id.cityname)
        restphno=findViewById<TextInputEditText>(R.id.phno)
        restemail=findViewById<TextInputEditText>(R.id.email)
        restcost=findViewById<TextInputEditText>(R.id.costfor2)
        resttime=findViewById<TextInputEditText>(R.id.avgtime)
        register=findViewById<Button>(R.id.register)
        imageView=findViewById(R.id.imageView)
        upload=findViewById<Button>(R.id.uploadImage)
        imageView.setOnClickListener {
            addImage()
        }
        upload.setOnClickListener {
            uploadImage()
        }
        register.setOnClickListener {
            saveData()
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Registered Successfully")
            builder.setMessage("Wait until Admin Approves your Restaurant")
            builder.setPositiveButton("Ok"){
                dialog,which -> startActivity(Intent(this, RestaurantLoginActivity::class.java))
            }
            val alert : AlertDialog = builder.create()
            alert.setCancelable(false)
            alert.show()
        }
    }
    private fun uploadImage() {
        val pd= ProgressDialog(this)
        pd.setTitle("Uploading")
        pd.show()

        val randomKey: String = UUID.randomUUID().toString()
        val imageRef:StorageReference = FirebaseStorage.getInstance().reference.child("Hotel Image/"+randomKey)
        val uploadTask= imageRef.putFile(filePath)
            .addOnProgressListener { task ->
                val progress = (100.0 * task.bytesTransferred)/ task.totalByteCount
                pd.setMessage("Uploaded ${progress.toInt()}%")
            }

        uploadTask.continueWithTask(){task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }

            .addOnCompleteListener(){ task ->
                if (task.isSuccessful) {
                    pd.dismiss()
                    imageUrl = task.getResult().toString()
                }
            }
            .addOnSuccessListener {
                pd.dismiss()
                Toast.makeText(this,"Image Uploaded",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{ task ->
                pd.dismiss()
                Toast.makeText(this,task.message,Toast.LENGTH_SHORT).show()
            }
    }

    private fun addImage() {
        val i= Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i,"Choose image"),71)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 71 && resultCode == Activity.RESULT_OK && data != null) {
            filePath = data.data!!
            val imageUri = data.data
            imageView.setImageURI(imageUri)
            imageUrl = imageUri.toString()
        }
    }

    fun saveData(){
        if(restname.text?.isEmpty()!!){
            restname.error="Please the restaurant name"
            restname.requestFocus()
            return
        }
        if(restcity.text?.isEmpty()!!){
            restcity.error="Please the city name"
            restcity.requestFocus()
            return
        }
        if(restphno.text?.isEmpty()!!){
            restphno.error="Please the phone number"
            restphno.requestFocus()
            return
        }
        if(restemail.text?.isEmpty()!!){
            restemail.error="Please the email"
            restemail.requestFocus()
            return
        }
        if(restcost.text?.isEmpty()!!){
            restcost.error="Please the restaurant cost"
            restcost.requestFocus()
            return
        }
        if(resttime.text?.isEmpty()!!){
            resttime.error="Please the restaurant time"
            resttime.requestFocus()
            return
        }
        restaurantDataModel= RestaurantDataModel(imageUrl,restname.text.toString(),restcity.text.toString(),"4.0",restcost.text.toString(),restidd1,resttime.text.toString(),restemail.text.toString(),restphno.text.toString(),false)
        restidd1++
        hashMap.put("restaurantimage",restaurantDataModel.restaurantimage)
        hashMap.put("restaurantname",restaurantDataModel.name)
        hashMap.put("cityname",restaurantDataModel.CityName)
        hashMap.put("rating",restaurantDataModel.rating)
        hashMap.put("cost",restaurantDataModel.cost)
        hashMap.put("restid",restaurantDataModel.res_id.toString())
        hashMap.put("time",restaurantDataModel.time)
        hashMap.put("email",restaurantDataModel.email)
        hashMap.put("phonenumber",restaurantDataModel.phonenumber)
        hashMap.put("status",restaurantDataModel.status.toString())
        adminDb!!.push().setValue(hashMap)
            .addOnCompleteListener {
//                Toast.makeText(this,"Successfully Registered Wait for the Admin Approval",Toast.LENGTH_LONG).show()
            }
    }

    override fun onResume() {
        super.onResume()
        sharedPreferences = this.getSharedPreferences("RestIdno1", Context.MODE_PRIVATE)
        restidd1=sharedPreferences.getInt("idd",1)
    }

    override fun onPause() {
        super.onPause()
        sharedPreferences = this.getSharedPreferences("RestIdno1", Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putInt("idd", restidd1)
        editor.apply()
        editor.commit()
    }
}