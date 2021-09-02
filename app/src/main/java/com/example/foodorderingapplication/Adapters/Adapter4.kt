package com.example.foodorderingapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import android.widget.*
import com.example.foodorderingapplication.Models.ItemsModel
import com.example.foodorderingapplication.Interfaces.OnClickInterface
import com.example.foodorderingapplication.R
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class Adapter4(private val context: Context, private var data:List<ItemsModel>, val clicklistener: OnClickInterface): RecyclerView.Adapter<Adapter4.ImageViewHolder>(),
    Filterable {
    var filterdata:List<ItemsModel> = data

    class ImageViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val itemimg=itemView.findViewById<ImageView>(R.id.itemimage)
        val itemname=itemView.findViewById<TextView>(R.id.itemname)
        val itemcost=itemView.findViewById<TextView>(R.id.itemcost)
        val quantity = itemView.findViewById<TextView>(R.id.t_count)
        //        val addBtn = itemView.findViewById<Button>(R.id.additem)
        val category:ImageView = itemView.findViewById(R.id.type)
        val plus = itemView.findViewById<ImageView>(R.id.i_plus)
        val addbtn=itemView.findViewById<Button>(R.id.addbtn)
        val minadd=itemView.findViewById<RelativeLayout>(R.id.minus_plus)
        val minus = itemView.findViewById<ImageView>(R.id.i_minus)
        fun bindView(image: ItemsModel)
        {
            Picasso.get().load(image.imagefood).into(itemimg)
            itemname.setText(image.itemname)
            itemcost.setText(image.itemcost)
            if(image.category=="Veg") {
                Picasso.get().load(R.drawable.veg1).into(category)
            }
            else{
                Picasso.get().load(R.drawable.nonveg1).into(category)
            }

        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.items_row_view,parent,false))


    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(data[position])

//        holder.addBtn.setOnClickListener {
//            clicklistener.onAdd(position)
//
//        }
        holder.addbtn.setOnClickListener {
            holder.addbtn.visibility=View.INVISIBLE
            holder.minadd.visibility=View.VISIBLE
            val quantity1 = (holder.quantity.text.toString().toInt()).toString()
            data[position].item_quantity = 1
            holder.quantity.setText(quantity1)

            clicklistener.addQuantity(position,quantity1)

        }

        holder.plus.setOnClickListener{

            val quantity1 = (holder.quantity.text.toString().toInt()+1).toString()
            data[position].item_quantity += 1
            holder.quantity.setText(quantity1)

            clicklistener.addQuantity(position,quantity1)
        }
        holder.minus.setOnClickListener {

            var quantity1 = holder.quantity.text.toString().toInt()
            if(quantity1.equals(1)){
                holder.addbtn.visibility=View.VISIBLE
                holder.minadd.visibility=View.INVISIBLE
                clicklistener.decreaseQuantity(position,quantity1.toString())
                data[position].item_quantity=1
                holder.quantity.setText(1.toString())

            }
            else {
                clicklistener.decreaseQuantity(position,quantity1.toString())
                quantity1 -= 1
                data[position].item_quantity  -= 1
                holder.quantity.setText(quantity1.toString())

            }


//            clicklistener.decreaseQuantity(position)
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
                    val result= ArrayList<ItemsModel>()
                    for (row in data)
                    {
                        if (row.itemcost.toLowerCase(Locale.ROOT).contains(search.toLowerCase(Locale.ROOT)))
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
                    data=results.values as ArrayList<ItemsModel>
                }
            }

        }
    }

}