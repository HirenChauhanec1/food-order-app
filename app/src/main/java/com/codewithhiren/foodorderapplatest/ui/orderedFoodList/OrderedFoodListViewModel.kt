package com.codewithhiren.foodorderapplatest.ui.orderedFoodList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithhiren.foodorderapplatest.model.AddOrUpdateRequest
import com.codewithhiren.foodorderapplatest.model.OrderedFoodListResponse
import com.codewithhiren.foodorderapplatest.repository.Repository
import com.codewithhiren.foodorderapplatest.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderedFoodListViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {

    var orderedFoodList = MutableStateFlow<MyResponse<OrderedFoodListResponse>>(MyResponse.Loading())
        private set

    var rvPosition = 0

    init {
        getOrderedFoodList()
    }

    fun getOrderedFoodList() {
        viewModelScope.launch {
            repository.getOrderedFoodList().collect {
                orderedFoodList.emit(it)
            }
        }
    }

    suspend fun deleteOrder(id: Int) = repository.deleteOrder(id)
    suspend fun updateOrder(id: Int, addOrUpdateRequest: AddOrUpdateRequest) = repository.updateOrder(id, addOrUpdateRequest)
    suspend fun addOrder(addOrUpdateRequest: AddOrUpdateRequest) = repository.addOrder(addOrUpdateRequest)

}