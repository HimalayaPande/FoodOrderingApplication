package com.example.foodorderingapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapplication.Models.ApprovedRestaurantDataModel
import com.example.foodorderingapplication.R
import com.squareup.picasso.Picasso

class ApprovedRestaurantAdapter(private val context: Context, private val data:List<ApprovedRestaurantDataModel>): RecyclerView.Adapter<ApprovedRestaurantAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val resimg=itemView.findViewById<ImageView>(R.id.imageView)
        val restname=itemView.findViewById<TextView>(R.id.textView)
        val restcity=itemView.findViewById<TextView>(R.id.textView2)
        val resratingim=itemView.findViewById<ImageView>(R.id.imageView2)
        val resrating=itemView.findViewById<TextView>(R.id.textView3)
        val cost=itemView.findViewById<TextView>(R.id.textView4)
        val time: Button = itemView.findViewById(R.id.timee)

        fun bindView(image: ApprovedRestaurantDataModel)
        {
            Picasso.get().load(image.restaurantimage).into(resimg)
            restname.setText(image.name)
            restcity.setText(image.CityName)
            resratingim.setImageResource(image.ratingimage)
            resrating.setText(image.rating.toString())
            cost.setText(image.cost)
            time.setText(image.time)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_approved_restaurant_row,parent,false))


    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(data[position])


    }

}