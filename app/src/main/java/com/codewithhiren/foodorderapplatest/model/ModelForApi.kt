package com.codewithhiren.foodorderapplatest.model

import java.io.Serializable

data class AddOrUpdateRequest(
    val foodName: String,
    val foodPrice: String,
    val quantity: String,
    val imageurl: String,
    val customerName: String,
    val customerNum: String
)

data class OrderedFoodListResponse(
    val error: String,
    val orderedFoodList: List<FetchOrderItemResponse>
)

data class FetchOrderItemResponse(
    val id: Int,
    val foodname: String,
    val foodprice: String,
    val quantity: String,
    val imageurl: String,
    val customername: String,
    val customernum: String
) : Serializable


data class FoodListResponse(
    val error: String,
    val foodlist: List<FoodListItemResponse>
)

data class FoodListItemResponse(
    val id: Int,
    val extra: String,
    val foodname: String,
    val foodprice: String,
    val imageurl: String
) : Serializable

data class OrderResponse(
    var error: String,
    var message: String
)






