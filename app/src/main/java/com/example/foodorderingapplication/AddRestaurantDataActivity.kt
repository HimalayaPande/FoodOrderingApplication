package com.example.foodorderingapplication

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.example.foodorderingapplication.*
import com.example.foodorderingapplication.Models.RestaurantItemDataModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class AddRestaurantDataActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    private lateinit var mAuth: FirebaseAuth
    lateinit var restaurantItemDataModel: RestaurantItemDataModel
    var itemidd1:Int = 1
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var storageReference: StorageReference
    private lateinit var imageUrl: String
    private lateinit var filePath : Uri
    var hashMap1 = HashMap<String,String>()
    val itemdb = database.getReference().child("ItemsTable")
    lateinit var item_name: EditText
    lateinit var rest_id: String
    lateinit var item_price: EditText
    lateinit var item_category: RadioGroup
    lateinit var radioButton: RadioButton
    lateinit var uploaditemimage: Button
    lateinit var itemimage: ImageView
    lateinit var item_add: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_restaurant_data)
        supportActionBar?.setTitle("Alfa Food Service")
        mAuth= FirebaseAuth.getInstance()
        rest_id=intent.getStringExtra("restidno").toString()
        item_name=findViewById(R.id.name)
        item_price=findViewById(R.id.price)
        item_category=findViewById(R.id.radiogroup)
        item_add=findViewById(R.id.additem)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.customerorders -> {
                    val intent=Intent(applicationContext, RestaurantOrderHistoryActivity::class.java)
                    intent.putExtra("restid3",rest_id)
                startActivity(intent)

                overridePendingTransition(0, 0)
                return@OnNavigationItemSelectedListener true
            }
                R.id.customerfeedback ->{
                    val intent=Intent(applicationContext, RestaurantFeedbackActivity::class.java)
                    intent.putExtra("restid4",rest_id)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.acceptreject1 ->{
                    val intent=Intent(applicationContext, AcceptOrRejectOrderActivity::class.java)
                    intent.putExtra("restid5",rest_id)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            true
        })


        uploaditemimage=findViewById(R.id.uploaditemimage)
        itemimage=findViewById(R.id.itemimageView)
        itemimage.setOnClickListener {
            addImage()
        }
        uploaditemimage.setOnClickListener {
            uploadImage()
        }
        item_add.setOnClickListener {
            val selectedoption: Int=item_category!!.checkedRadioButtonId
            radioButton=findViewById(selectedoption)
            saveData()
            item_name.text.clear()
            item_price.text.clear()
            item_category.clearCheck()
            itemimage.setImageResource(R.drawable.ic_baseline_add_a_photo_24)
        }

    }

    private fun saveData() {
        if(item_name.text?.isEmpty()!!){
            item_name.error="Please Enter the item name"
            item_name.requestFocus()
            return
        }
        if(item_price.text?.isEmpty()!!){
            item_price.error="Please Enter the price"
            item_price.requestFocus()
            return
        }
        if(radioButton.text?.isEmpty()!!){
            radioButton.error="Please Enter the Category"
            radioButton.requestFocus()
            return
        }
        hashMap1.put("rest_id",rest_id)
        hashMap1.put("item_id",itemidd1.toString())
        hashMap1.put("item_name",item_name.text.toString())
        hashMap1.put("item_price",item_price.text.toString())
        hashMap1.put("item_category",radioButton.text.toString())
        hashMap1.put("item_image",imageUrl)
        itemidd1++
        itemdb.push().setValue(hashMap1).addOnCompleteListener {
            Toast.makeText(this,"Item Added Successfully",Toast.LENGTH_LONG).show()
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
            itemimage.setImageURI(imageUri)
            imageUrl = imageUri.toString()
        }
    }
    override fun onResume() {
        super.onResume()
        sharedPreferences = this.getSharedPreferences("ItemIdno1", Context.MODE_PRIVATE)
        itemidd1=sharedPreferences.getInt("id1",1)
    }

    override fun onPause() {
        super.onPause()
        sharedPreferences = this.getSharedPreferences("ItemIdno1", Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putInt("id1", itemidd1)
        editor.apply()
        editor.commit()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.signout ->{
                mAuth.signOut()
                finish()
                startActivity(Intent(this, RestaurantLoginActivity::class.java))
                true
            }
            else -> return false
        }

    }

}