package com.codewithhiren.foodorderapplatest.ui.foodList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codewithhiren.foodorderapplatest.databinding.FragmentFoodListBinding
import com.codewithhiren.foodorderapplatest.model.FoodListItemResponse
import com.codewithhiren.foodorderapplatest.model.FoodListResponse
import com.codewithhiren.foodorderapplatest.utils.MyResponse
import com.codewithhiren.foodorderapplatest.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class FoodListFragment : Fragment() {

    private val binding by lazy { FragmentFoodListBinding.inflate(layoutInflater) }

    private lateinit var foodListAdapter: FoodListAdapter

    @Inject
    lateinit var navController: NavController
    private val foodListViewModel: FoodListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("chauhan","food list fragment")
        bindData()
        bindObservers()
    }

    private fun bindData() {
        foodListAdapter = FoodListAdapter(requireContext(), ::makeOrder)
        binding.apply {
            rv.layoutManager = LinearLayoutManager(requireContext())
            rv.setHasFixedSize(true)
            rv.adapter = foodListAdapter
        }
    }

    private fun bindObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                foodListViewModel.foodList.collect { myResponse: MyResponse<FoodListResponse> ->
                    hideProgressBar()
                    when (myResponse) {
                        is MyResponse.Success -> {
                            foodListAdapter.submitList(myResponse.data.foodlist)
                            binding.rv.scrollToPosition(foodListViewModel.rvPosition.value)
                        }

                        is MyResponse.Error -> showToast(myResponse.errorMsg)
                        is MyResponse.Loading -> showProgressBar()
                    }
                }
            }
        }
    }

    private fun makeOrder(foodListItemResponse: FoodListItemResponse) {
        navController.navigate(
            FoodListFragmentDirections.actionFoodListFragmentToMakeOrderFragment(foodListItemData = foodListItemResponse)
        )
    }

    private fun hideProgressBar() {
        binding.pb.isVisible = false
    }

    private fun showProgressBar() {
        binding.pb.isVisible = true
    }

    override fun onStop() {
        super.onStop()
        foodListViewModel.rvPosition.value =
            (binding.rv.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

    }
}