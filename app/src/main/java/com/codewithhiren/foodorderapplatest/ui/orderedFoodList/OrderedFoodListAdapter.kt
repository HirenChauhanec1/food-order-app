package com.codewithhiren.foodorderapplatest.ui.orderedFoodList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codewithhiren.foodorderapplatest.R
import com.codewithhiren.foodorderapplatest.databinding.OrderedFoodListItemBinding
import com.codewithhiren.foodorderapplatest.model.FetchOrderItemResponse
import com.squareup.picasso.Picasso
import kotlin.reflect.KFunction2


class OrderedFoodListAdapter(
    private val context: Context,
    private val showDialog: KFunction2<Int, FetchOrderItemResponse, Unit>
) : ListAdapter<FetchOrderItemResponse,OrderedFoodListAdapter.ViewHolder>(comparator){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OrderedFoodListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder (private val binding : OrderedFoodListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: FetchOrderItemResponse) {
            binding.apply {
                item.apply {
                    Picasso.get().load(imageurl).into(ivFoodImage)
                    tvFoodName.text = foodname
                    tvOrderNumber.text = context.getString(R.string.order_number_with_value, id.toString())
                    tvRupeeSign.text = context.getString(R.string.foodprice_with_value, foodprice)
                    tvQuantity.text = context.getString(R.string.quantity_with_value, quantity)

                    cvMain.setOnClickListener { showDialog(id,item) }
                }
            }
        }
    }
}
val comparator = object : DiffUtil.ItemCallback<FetchOrderItemResponse>() {
    override fun areItemsTheSame(oldItem: FetchOrderItemResponse, newItem: FetchOrderItemResponse): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: FetchOrderItemResponse, newItem: FetchOrderItemResponse): Boolean {
        return oldItem == newItem
    }
}

