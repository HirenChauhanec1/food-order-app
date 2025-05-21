package com.codewithhiren.foodorderapplatest


import com.codewithhiren.foodorderapplatest.model.FoodListResponse
import com.codewithhiren.foodorderapplatest.model.OrderedFoodListResponse
import com.codewithhiren.foodorderapplatest.model.OrderResponse
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface FoodApi {

    @FormUrlEncoded
    @POST("addOrder.php")
    suspend fun addOrder(@FieldMap map:Map<String,String>) : Response<OrderResponse>

    @GET("fetchlist.php")
    suspend fun getFoodList() : Response<FoodListResponse>

    @FormUrlEncoded
    @POST("updateOrder.php")
    suspend fun updateOrder(@Query("id") id : Int, @FieldMap map:Map<String,String>) : Response<OrderResponse>

    @GET("deleteOrder.php")
    suspend fun deleteOrder(@Query("id") id:Int) : Response<OrderResponse>

    @GET("fetchOrderedFood.php")
    suspend fun getOrderedFoodList() : Response<OrderedFoodListResponse>

}