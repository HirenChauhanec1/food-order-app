package com.codewithhiren.foodorderapplatest.ui.foodList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithhiren.foodorderapplatest.model.FoodListResponse
import com.codewithhiren.foodorderapplatest.repository.Repository
import com.codewithhiren.foodorderapplatest.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(private val repository: Repository) :   ViewModel(){

    var foodList = MutableStateFlow<MyResponse<FoodListResponse>>(MyResponse.Loading())
        private set

    val rvPosition = MutableStateFlow(0)

    init {
        viewModelScope.launch {
            repository.getFoodList().collect {
                foodList.value = it
            }
        }
    }


}