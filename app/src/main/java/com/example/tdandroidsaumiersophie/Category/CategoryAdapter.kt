package com.example.tdandroidsaumiersophie.Category

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tdandroidsaumiersophie.network.Dish
import com.squareup.picasso.Picasso
import com.example.tdandroidsaumiersophie.databinding.DishesCellBinding


class CategoryAdapter(private val entries: List<Dish>,
                      private val entryClickListener: (Dish) -> Unit)
    : RecyclerView.Adapter<CategoryAdapter.DishesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishesViewHolder {
        return DishesViewHolder(DishesCellBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DishesViewHolder, position: Int) {
        val dish = entries[position]
        holder.layout.setOnClickListener {
            entryClickListener.invoke(dish)
        }
        holder.bind(dish)

    }

    override fun getItemCount(): Int {
        return entries.count()
    }

    class DishesViewHolder(dishesBinding: DishesCellBinding): RecyclerView.ViewHolder(dishesBinding.root) {
        val titleView: TextView = dishesBinding.dishName
        val priceView: TextView = dishesBinding.dishPrice
        val imageView: ImageView = dishesBinding.imageView2
        val layout = dishesBinding.root

        fun bind(dish: Dish) {
            titleView.text = dish.name
            priceView.text = "${dish.prices.first().price} €" // dish.prices.first().price + " €"
            var url: String? = null
            if(dish.images.isNotEmpty() && dish.images[0].isNotEmpty()){
                url=dish.images[0]

            }
            Picasso.get().load(url).placeholder(com.example.tdandroidsaumiersophie.R.drawable.ic_noavailable).into(imageView)
        }

    }
}