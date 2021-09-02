package com.example.foodorderingapplication.Adapters


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import android.widget.*
import com.example.foodorderingapplication.Models.CartModel
import com.example.foodorderingapplication.Interfaces.OnClickInterface
import com.example.foodorderingapplication.R
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class CartAdapter(private val context: Context, private var data:ArrayList<CartModel>, val clicklistener: OnClickInterface): RecyclerView.Adapter<CartAdapter.ImageViewHolder>(),
        Filterable {
    var filterdata:ArrayList<CartModel> = data

    class ImageViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val itemname=itemView.findViewById<TextView>(R.id.item_name)
        val itemcost=itemView.findViewById<TextView>(R.id.item_price)
        val category_img = itemView.findViewById<ImageView>(R.id.category)
        val quantity = itemView.findViewById<TextView>(R.id.t_count)
        //        val addBtn = itemView.findViewById<Button>(R.id.additem)
        val plus = itemView.findViewById<ImageView>(R.id.i_plus)
        val minus = itemView.findViewById<ImageView>(R.id.i_minus)
        @SuppressLint("SetTextI18n")
        fun bindView(image: CartModel)
        {
            if(image.category=="Veg") {
                Picasso.get().load(R.drawable.veg1).into(category_img)
            }
            else{
                Picasso.get().load(R.drawable.nonveg1).into(category_img)
            }
            quantity.setText(image.item_quantity.toString())
            itemname.setText(image.itemname)
            itemcost.setText(image.itemcost)
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
            ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_items_row_view,parent,false))


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(data[position])

//        holder.addBtn.setOnClickListener {
//            clicklistener.onAdd(position)
//
//        }

        holder.plus.setOnClickListener{

            val quantity1 = (holder.quantity.text.toString().toInt()+1).toString()
            data[position].item_quantity += 1
            holder.quantity.setText(quantity1)

            clicklistener.addQuantity(position,quantity1)

        }
        holder.minus.setOnClickListener {

            var quantity1 = holder.quantity.text.toString().toInt()
            if(quantity1 <= 1 ){
                clicklistener.decreaseQuantity(position,"1")
                data.remove(data[position])
                    notifyDataSetChanged()
            }
            else {
                clicklistener.decreaseQuantity(position,quantity1.toString())
                quantity1 -= 1
                data[position].item_quantity  -= 1
                holder.quantity.setText(quantity1.toString())

            }

//            if(quantity1 <= 0){
//                data[position].item_quantity  = 0
//                quantity1 = 0
//            }


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
                    val result= ArrayList<CartModel>()
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
                    data=results.values as ArrayList<CartModel>
                }
            }

        }
    }

}
