package com.example.foodorderingapplication.Adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import android.widget.*
import com.example.foodorderingapplication.Interfaces.OnClickInterface
import com.example.foodorderingapplication.Models.OrderHistoryModel
import com.example.foodorderingapplication.R
import java.util.*
import kotlin.collections.ArrayList

class MyOrdersAdapter(private val context: Context, private var data:ArrayList<OrderHistoryModel>, private val clickListener: OnClickInterface): RecyclerView.Adapter<MyOrdersAdapter.ImageViewHolder>(),
    Filterable, OnClickInterface {
    var filterdata:ArrayList<OrderHistoryModel> = data

    class ImageViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val rateorder=itemView.findViewById<TextView>(R.id.rateorder)
        val  restname = itemView.findViewById<TextView>(R.id.restname)
        val itemList=itemView.findViewById<TextView>(R.id.items_list)
        val totalCost=itemView.findViewById<TextView>(R.id.total_cost)
        val orderedon = itemView.findViewById<TextView>(R.id.timeofoder)
        fun bindView(image: OrderHistoryModel)
        {
            restname.setText(image.rest_name)
            itemList.setText(image.itemlist)
            totalCost.setText(image.totalcost)
            orderedon.setText(image.orderedDate)
            rateorder.setText(image.rate)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.history_items_row_view,parent,false))


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(data[position])
        holder.rateorder.setOnClickListener {
            clickListener.onClick(position)
        }

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
                    val result= ArrayList<OrderHistoryModel>()
                    for (row in data)
                    {
                        if (row.totalcost.toLowerCase(Locale.ROOT).contains(search.toLowerCase(Locale.ROOT)))
                        {
                            result.add(row)
                        }

                    }
                    data=result

                }
                val filterresults=FilterResults()
                filterresults.values=data
                return filterresults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null) {
                    data=results.values as ArrayList<OrderHistoryModel>
                }
            }

        }
    }

    override fun onClick(name: Int) {

    }

    override fun onAdd(addItemIndex: Int) {
        TODO("Not yet implemented")
    }

    override fun addQuantity(position: Int, quantity: String) {
        TODO("Not yet implemented")
    }

    override fun decreaseQuantity(position: Int, quantity: String) {
        TODO("Not yet implemented")
    }

}
