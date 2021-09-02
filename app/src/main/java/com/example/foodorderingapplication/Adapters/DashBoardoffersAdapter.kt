package com.example.foodorderingapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapplication.Models.Image
import com.example.foodorderingapplication.R

class DashBoardoffersAdapter(private val context:Context,private val images:List<Image>):RecyclerView.Adapter<DashBoardoffersAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val img=itemView.findViewById<ImageView>(R.id.imagee)
        fun bindView(image: Image)
        {
            img.setImageResource(image.imagesrc)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.row_view1, parent, false)
        )


    override fun getItemCount(): Int =images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(images[position])
    }
}
