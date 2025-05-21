package com.codewithhiren.foodorderapplatest.ui.foodList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codewithhiren.foodorderapplatest.R
import com.codewithhiren.foodorderapplatest.databinding.FoodListItemBinding
import com.codewithhiren.foodorderapplatest.model.FoodListItemResponse
import com.squareup.picasso.Picasso

class FoodListAdapter(
    private val context: Context,
    private val makeOrder: (FoodListItemResponse) -> Unit
) :
    ListAdapter<FoodListItemResponse, FoodListAdapter.ViewHolder>(comparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FoodListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: FoodListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FoodListItemResponse) {
            binding.apply {
                item.apply {
                    Picasso.get().load(imageurl).into(ivFoodImage)
                    tvFoodName.text = foodname
                    tvPrice.text = context.getString(R.string.food_price_in_rupee, foodprice)
                    tvDesc.text = extra
                }
                cvOneItem.setOnClickListener { makeOrder(item) }
            }
        }
    }
}
val comparator = object : DiffUtil.ItemCallback<FoodListItemResponse>() {
    override fun areItemsTheSame(oldItem: FoodListItemResponse, newItem: FoodListItemResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FoodListItemResponse, newItem: FoodListItemResponse): Boolean {
        return oldItem == newItem
    }
}

