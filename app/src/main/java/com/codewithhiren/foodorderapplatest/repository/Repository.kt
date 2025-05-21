package com.codewithhiren.foodorderapplatest.repository

import com.codewithhiren.foodorderapplatest.model.AddOrUpdateRequest
import com.codewithhiren.foodorderapplatest.model.FoodListResponse
import com.codewithhiren.foodorderapplatest.model.OrderResponse
import com.codewithhiren.foodorderapplatest.model.OrderedFoodListResponse
import com.codewithhiren.foodorderapplatest.utils.MyResponse
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getFoodList(): Flow<MyResponse<FoodListResponse>>
    suspend fun addOrder(addOrUpdateRequest: AddOrUpdateRequest): Flow<MyResponse<OrderResponse>>
    suspend fun getOrderedFoodList(): Flow<MyResponse<OrderedFoodListResponse>>
    suspend fun updateOrder(id: Int, addOrUpdateRequest: AddOrUpdateRequest): Flow<MyResponse<OrderResponse>>
    suspend fun deleteOrder(id: Int): Flow<MyResponse<OrderResponse>>

}