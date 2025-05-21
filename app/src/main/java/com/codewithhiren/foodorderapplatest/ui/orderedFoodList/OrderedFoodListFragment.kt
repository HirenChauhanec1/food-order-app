package com.codewithhiren.foodorderapplatest.ui.orderedFoodList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codewithhiren.foodorderapplatest.R
import com.codewithhiren.foodorderapplatest.databinding.FragmentOrderedFoodBinding
import com.codewithhiren.foodorderapplatest.model.FetchOrderItemResponse
import com.codewithhiren.foodorderapplatest.model.OrderResponse
import com.codewithhiren.foodorderapplatest.model.OrderedFoodListResponse
import com.codewithhiren.foodorderapplatest.utils.MyResponse
import com.codewithhiren.foodorderapplatest.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OrderedFoodListFragment : Fragment() {

    private val binding by lazy { FragmentOrderedFoodBinding.inflate(layoutInflater) }
    private lateinit var orderedFoodListAdapter: OrderedFoodListAdapter

    @Inject
    lateinit var navController: NavController
    private val orderedFoodListViewModel: OrderedFoodListViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindData()
        bindObservers()
    }

    private fun bindData() {
        orderedFoodListAdapter = OrderedFoodListAdapter(requireContext(), ::showDialog)
        binding.apply {
            rvOrderedFood.layoutManager = LinearLayoutManager(requireContext())
            rvOrderedFood.setHasFixedSize(true)
            rvOrderedFood.adapter = orderedFoodListAdapter
        }

    }

    private fun bindObservers() {
        lifecycleScope.launch {
            orderedFoodListViewModel.orderedFoodList
                .collect { myResponse: MyResponse<OrderedFoodListResponse> ->
                    hideProgressBar()
                    when (myResponse) {
                        is MyResponse.Success -> {
                            orderedFoodListAdapter.submitList(myResponse.data.orderedFoodList.reversed())
                            binding.rvOrderedFood.scrollToPosition(orderedFoodListViewModel.rvPosition)
                        }

                        is MyResponse.Error -> showToast(myResponse.errorMsg)
                        is MyResponse.Loading -> showProgressBar()
                    }

                }
        }
    }

    private fun showDialog(id: Int, fetchOrderItemResponse: FetchOrderItemResponse) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete or Update ?")
            .setMessage("Are you want to delete or update this order ?")
            .setIcon(AppCompatResources.getDrawable(requireContext(), R.drawable.delete_sign))
            .setPositiveButton("Update") { dialog, _ ->
                updateOrder(fetchOrderItemResponse)
                dialog.dismiss()
            }
            .setNegativeButton("Delete") { dialog, _ ->
                deleteOrder(id)
                dialog.dismiss()
            }
            .setNeutralButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()

    }

    private fun updateOrder(fetchOrderItemResponse: FetchOrderItemResponse) {
        navController.navigate(
            OrderedFoodListFragmentDirections
                .actionOrderedFoodListFragmentToMakeOrderFragment(updateOrderData = fetchOrderItemResponse)
        )
    }

    private fun deleteOrder(id: Int) {
        lifecycleScope.launch {
            orderedFoodListViewModel.deleteOrder(id)
                .collect { myResponse: MyResponse<OrderResponse> ->
                    hideProgressBar()
                    when (myResponse) {
                        is MyResponse.Success -> {
                            showToast(myResponse.data.message)
                            orderedFoodListViewModel.getOrderedFoodList()

                        }
                        is MyResponse.Error -> showToast(myResponse.errorMsg)
                        is MyResponse.Loading -> showProgressBar()
                    }
                }
        }
    }

    private fun hideProgressBar() { binding.pb.isVisible = false }

    private fun showProgressBar() { binding.pb.isVisible = true }

    override fun onStop() {
        super.onStop()
        orderedFoodListViewModel.rvPosition =
            (binding.rvOrderedFood.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
    }

}