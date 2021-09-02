package com.example.foodorderingapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.Models.ViewCustomerDetailsModel

class ViewCustomerDetailsAdapter (private val context: Context, private val data:List<ViewCustomerDetailsModel>): RecyclerView.Adapter<ViewCustomerDetailsAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val name1=itemView.findViewById<TextView>(R.id.customername)
        val email1=itemView.findViewById<TextView>(R.id.Email)
        val phonenumber=itemView.findViewById<TextView>(R.id.phone)
        val address=itemView.findViewById<TextView>(R.id.Address)

        fun bindView(image: ViewCustomerDetailsModel)
        {
            name1.setText(image.name)
            email1.setText(image.email)
            phonenumber.setText(image.phonenumber)
            address.setText(image.city)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.view_customer_row,parent,false))


    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(data[position])


    }

}