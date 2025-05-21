package com.codewithhiren.foodorderapplatest.repository

import com.codewithhiren.foodorderapplatest.FoodApi
import com.codewithhiren.foodorderapplatest.model.AddOrUpdateRequest
import com.codewithhiren.foodorderapplatest.utils.HelperClass
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val foodApi: FoodApi
) : Repository , GenericApiHandleResponse{

    override suspend fun getFoodList() =
            handleResponse { foodApi.getFoodList() }

    override suspend fun addOrder(addOrUpdateRequest: AddOrUpdateRequest) =
        handleResponse { foodApi.addOrder(HelperClass.toMap(addOrUpdateRequest)) }

    override suspend fun getOrderedFoodList() =
        handleResponse { foodApi.getOrderedFoodList() }

    override suspend fun updateOrder(id: Int, addOrUpdateRequest: AddOrUpdateRequest) =
        handleResponse { foodApi.updateOrder(id, HelperClass.toMap(addOrUpdateRequest)) }

    override suspend fun deleteOrder(id: Int) =
        handleResponse { foodApi.deleteOrder(id) }

}


