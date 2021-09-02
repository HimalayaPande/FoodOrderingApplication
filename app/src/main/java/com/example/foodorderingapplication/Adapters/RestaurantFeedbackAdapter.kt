package com.example.foodorderingapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.Models.RestaurantFeedbackModel
import java.util.*
import kotlin.collections.ArrayList

class RestaurantFeedbackAdapter(private val context: Context, private var data:ArrayList<RestaurantFeedbackModel>): RecyclerView.Adapter<RestaurantFeedbackAdapter.ImageViewHolder>(),
        Filterable {
    var filterdata:ArrayList<RestaurantFeedbackModel> = data

    class ImageViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val  restname = itemView.findViewById<TextView>(R.id.restname)
        val itemList=itemView.findViewById<TextView>(R.id.items_list)
        val totalCost=itemView.findViewById<TextView>(R.id.total_cost)
        val rating = itemView.findViewById<TextView>(R.id.ratingoforder)
        val comments = itemView.findViewById<TextView>(R.id.comments2)
        fun bindView(image: RestaurantFeedbackModel)
        {
            restname.setText(image.restname)
            itemList.setText(image.item_list)
            totalCost.setText(image.totalcost)
            rating.setText(image.customerrating)
            comments.setText(image.comment)
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
            ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.restaurant_feedback_row,parent,false))


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val search=constraint.toString()
                if(search.isEmpty())
                {
                    data=filterdata
                }
                else
                {
                    val result= ArrayList<RestaurantFeedbackModel>()
                    for (row in data)
                    {
                        if (row.totalcost.toLowerCase(Locale.ROOT).contains(search.toLowerCase(Locale.ROOT)))
                        {
                            result.add(row)
                        }

                    }
                    data=result

                }
                val filterresults= FilterResults()
                filterresults.values=data
                return filterresults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null) {
                    data=results.values as ArrayList<RestaurantFeedbackModel>
                }
            }

        }
    }
}
