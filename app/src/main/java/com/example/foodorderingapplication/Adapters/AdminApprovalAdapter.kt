package com.example.foodorderingapplication.Adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapplication.Models.AdminApprovalDataModel
import com.example.foodorderingapplication.Interfaces.OnClickInterface
import com.example.foodorderingapplication.R
import com.squareup.picasso.Picasso


class AdminApprovalAdapter(private val context: Context, private val data:List<AdminApprovalDataModel>, val clicklistener: OnClickInterface): RecyclerView.Adapter<AdminApprovalAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val resimg=itemView.findViewById<ImageView>(R.id.imageview)
        val restname=itemView.findViewById<TextView>(R.id.restnamee)
        val restcityname=itemView.findViewById<TextView>(R.id.restcityname)
        val viewdetails: Button = itemView.findViewById(R.id.viewdetails)

        fun bindView(image: AdminApprovalDataModel)
        {
            Picasso.get().load(image.restaurantimage).into(resimg)
            restname.setText(image.name)
            restcityname.setText(image.CityName)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.adminapproval_row,parent,false))

    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(data[position])
        holder.viewdetails.setOnClickListener {
            clicklistener.onClick(position)
        }

    }

}