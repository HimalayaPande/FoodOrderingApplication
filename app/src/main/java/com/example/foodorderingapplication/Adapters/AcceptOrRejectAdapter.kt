package com.example.foodorderingapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapplication.Interfaces.AcceptOrRejectInterface
import com.example.foodorderingapplication.Models.AcceptOrRejectModel
import com.example.foodorderingapplication.R
import java.util.*
import kotlin.collections.ArrayList

class AcceptOrRejectAdapter(private val context: Context, private var data:ArrayList<AcceptOrRejectModel>, private val clickListener: AcceptOrRejectInterface): RecyclerView.Adapter<AcceptOrRejectAdapter.ImageViewHolder>(),
        Filterable, AcceptOrRejectInterface {
    var filterdata:ArrayList<AcceptOrRejectModel> = data
    class ImageViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val  restname = itemView.findViewById<TextView>(R.id.restname)
        val itemList=itemView.findViewById<TextView>(R.id.items_list)
        val totalCost=itemView.findViewById<TextView>(R.id.total_cost)
        val orderedon = itemView.findViewById<TextView>(R.id.timeofoder)
        val accept = itemView.findViewById<Button>(R.id.accept)
        val reject = itemView.findViewById<Button>(R.id.reject)
        fun bindView(image: AcceptOrRejectModel)
        {
            restname.setText(image.rest_name)
            itemList.setText(image.itemlist)
            totalCost.setText(image.totalcost)
            orderedon.setText(image.orderedDate)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
            ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.acceptorreject_row,parent,false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(data[position])
        holder.accept.setOnClickListener {
            clickListener.OnAccept(position)

        }
        holder.reject.setOnClickListener {
            clickListener.OnReject(position)

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
                    val result= ArrayList<AcceptOrRejectModel>()
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
                    data=results.values as ArrayList<AcceptOrRejectModel>
                }
            }

        }
    }

    override fun OnAccept(position: Int) {

    }

    override fun OnReject(position: Int) {
        TODO("Not yet implemented")
    }

}
