package com.example.foodorderingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import com.example.foodorderingapplication.Fragments.CardFragment
import com.example.foodorderingapplication.Fragments.UpiFragment

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        supportActionBar?.setTitle(getString(R.string.alfa))
        val cardpay=findViewById<Button>(R.id.cardpay)
        val upi=findViewById<Button>(R.id.upi)
        val paytm=findViewById<Button>(R.id.Paytm)
        val phonepay=findViewById<Button>(R.id.Phonepay)
        val cod=findViewById<Button>(R.id.cashondelivery)
        val fragment = findViewById<FrameLayout>(R.id.fragment)
        val place_the_order=findViewById<Button>(R.id.submit)

        val manager=supportFragmentManager
        cardpay.setOnClickListener {
            val trans=manager.beginTransaction()
            val frag= CardFragment()
            cardpay.setBackgroundColor(resources.getColor(R.color.ratingHigh))
            upi.setBackgroundColor(resources.getColor(R.color.white))
            paytm.setBackgroundColor(resources.getColor(R.color.white))
            phonepay.setBackgroundColor(resources.getColor(R.color.white))
            cod.setBackgroundColor(resources.getColor(R.color.white))
            trans.replace(R.id.fragment,frag)
            fragment.visibility = View.VISIBLE
            trans.commit()
        }
        upi.setOnClickListener {
            val trans=manager.beginTransaction()
            val frag= UpiFragment()
            upi.setBackgroundColor(resources.getColor(R.color.ratingHigh))
            cardpay.setBackgroundColor(resources.getColor(R.color.white))
            paytm.setBackgroundColor(resources.getColor(R.color.white))
            phonepay.setBackgroundColor(resources.getColor(R.color.white))
            cod.setBackgroundColor(resources.getColor(R.color.white))
            trans.replace(R.id.fragment,frag)
            fragment.visibility = View.VISIBLE
            trans.commit()
        }
        paytm.setOnClickListener {
            val trans=manager.beginTransaction()
            val frag= UpiFragment()
            paytm.setBackgroundColor(resources.getColor(R.color.ratingHigh))
            upi.setBackgroundColor(resources.getColor(R.color.white))
            cardpay.setBackgroundColor(resources.getColor(R.color.white))
            phonepay.setBackgroundColor(resources.getColor(R.color.white))
            cod.setBackgroundColor(resources.getColor(R.color.white))
            trans.replace(R.id.fragment,frag)
            fragment.visibility = View.VISIBLE
            trans.commit()
        }
        phonepay.setOnClickListener {
            val trans=manager.beginTransaction()
            val frag= UpiFragment()
            phonepay.setBackgroundColor(resources.getColor(R.color.ratingHigh))
            upi.setBackgroundColor(resources.getColor(R.color.white))
            paytm.setBackgroundColor(resources.getColor(R.color.white))
            cardpay.setBackgroundColor(resources.getColor(R.color.white))
            cod.setBackgroundColor(resources.getColor(R.color.white))
            trans.replace(R.id.fragment,frag)
            fragment.visibility = View.VISIBLE
            trans.commit()
        }
        cod.setOnClickListener {
            val trans=manager.beginTransaction()
            val frag= UpiFragment()
            cod.setBackgroundColor(resources.getColor(R.color.ratingHigh))
            upi.setBackgroundColor(resources.getColor(R.color.white))
            paytm.setBackgroundColor(resources.getColor(R.color.white))
            cardpay.setBackgroundColor(resources.getColor(R.color.white))
            phonepay.setBackgroundColor(resources.getColor(R.color.white))
            fragment.visibility = View.INVISIBLE

        }
        place_the_order.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.alert_box,null)
            val dialogBuilder = AlertDialog.Builder(this).setView(dialogView)
            val mAlert = dialogBuilder.show()
            val btn = dialogView.findViewById<Button>(R.id.read_btn)

            btn.setOnClickListener {
                finish()
                startActivity(Intent(this, CustomerDashboard::class.java))
            }

        }
    }
}