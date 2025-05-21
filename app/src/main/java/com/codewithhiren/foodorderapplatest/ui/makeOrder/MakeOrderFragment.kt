package com.codewithhiren.foodorderapplatest.ui.makeOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.codewithhiren.foodorderapplatest.R
import com.codewithhiren.foodorderapplatest.databinding.FragmentMakeOrderBinding
import com.codewithhiren.foodorderapplatest.model.AddOrUpdateRequest
import com.codewithhiren.foodorderapplatest.model.OrderResponse
import com.codewithhiren.foodorderapplatest.ui.orderedFoodList.OrderedFoodListViewModel
import com.codewithhiren.foodorderapplatest.utils.MyResponse
import com.codewithhiren.foodorderapplatest.utils.showToast
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MakeOrderFragment : Fragment() {

    private val binding by lazy { FragmentMakeOrderBinding.inflate(layoutInflater) }

    @Inject
    lateinit var navController: NavController
    private val orderedFoodListViewModel: OrderedFoodListViewModel by activityViewModels()
    private val args: MakeOrderFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        clickListener()
    }

    private fun setData() {
        args.foodListItemData?.apply {
            binding.apply {
                Picasso.get().load(imageurl).into(ivFoodImage)
                tvFoodName.text = foodname
                tvDesc.text = extra
                tv50.text = getString(R.string.foodprice_with_value, foodprice)
            }
        }
        args.updateOrderData?.apply {
            binding.apply {
                Picasso.get().load(imageurl).into(ivFoodImage)
                tvFoodName.text = foodname
                tv50.text = getString(
                    R.string.foodprice_with_value,
                    (foodprice.toInt() / quantity.toInt()).toString()
                )
                tvDesc.text = getString(R.string.with_extra_cheese, foodname)
                etName.setText(customername)
                etPhone.setText(customernum)
                etQuantity.setText(quantity)
                btnPlaceOrder.text = getString(R.string.update_order)
            }
        }
    }

    private fun clickListener() {
        binding.apply {
            btnPlaceOrder.setOnClickListener {
                val name = etName.text.toString().trim()
                val phone = etPhone.text.toString().trim()
                val quantity = etQuantity.text.toString().trim()

                when {
                    name.isEmpty() -> showToast("Enter Name")
                    phone.isEmpty() -> showToast("Enter Phone")
                    quantity.isEmpty() -> showToast("Enter Quantity")
                    else -> {
                        if (btnPlaceOrder.text == getString(R.string.place_order)) {
                            lifecycleScope.launch {
                                orderedFoodListViewModel.addOrder(
                                    addOrUpdateRequest = args.foodListItemData!!.run {
                                        AddOrUpdateRequest(
                                            foodName = foodname,
                                            foodPrice = (foodprice.toInt() * quantity.toInt()).toString(),
                                            imageurl = imageurl,
                                            customerName = name,
                                            customerNum = phone,
                                            quantity = quantity
                                        )
                                    }
                                ).collect {
                                    handleResponse(it, Order.ADD)
                                }
                            }
                        } else {
                            lifecycleScope.launch {
                                orderedFoodListViewModel.updateOrder(
                                    id = args.updateOrderData!!.id,
                                    addOrUpdateRequest = args.updateOrderData!!.run {
                                        AddOrUpdateRequest(
                                            foodName = foodname,
                                            foodPrice = ((foodprice.toInt() / this.quantity.toInt()) * quantity
                                                .toInt()).toString(),
                                            imageurl = imageurl,
                                            customerName = name,
                                            customerNum = phone,
                                            quantity = quantity
                                        )
                                    }
                                ).collect {
                                    handleResponse(it, Order.UPDATE)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun handleResponse(myResponse: MyResponse<OrderResponse>, order: Order) {
        binding.apply {
            btnPlaceOrder.isClickable = true
            hideProgressBar()
            when (myResponse) {
                is MyResponse.Success -> {
                    showToast(myResponse.data.message)
                    orderedFoodListViewModel.getOrderedFoodList()
                    navController.popBackStack()
                    if (order == Order.ADD) {
                        orderedFoodListViewModel.rvPosition = 0
                        navController.navigate(R.id.orderedFoodListFragment)
                    }
                }

                is MyResponse.Error -> showToast(myResponse.errorMsg)
                is MyResponse.Loading -> {
                    showProgressBar()
                    btnPlaceOrder.isClickable = false
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.pb.isVisible = false
    }

    private fun showProgressBar() {
        binding.pb.isVisible = true
    }
}

enum class Order {
    ADD,
    UPDATE
}