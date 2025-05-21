package com.codewithhiren.foodorderapplatest.repository


import android.util.Log
import com.codewithhiren.foodorderapplatest.utils.HelperClass
import com.codewithhiren.foodorderapplatest.utils.MyResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okio.IOException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface GenericApiHandleResponse {

    suspend fun <T : Any> handleResponse(apiCall: suspend () -> Response<T>): Flow<MyResponse<T>> =
        callbackFlow {
            trySend(MyResponse.Loading())
            val emitValue = if (HelperClass.isInternetAvailable()) {
                try {
                    val result = apiCall()
                    if (result.isSuccessful && result.body() != null) {
                        MyResponse.Success(result.body()!!)
                    } else
                        if (result.errorBody() != null) {
                            val resultJson = JSONObject(result.errorBody()!!.charStream().readText())
                            val error = resultJson.getString("message")
                            MyResponse.Error(error)
                        } else
                            MyResponse.Error(result.message())

                } catch (e: IOException) {
                    val exception = when (e) {
                        is UnknownHostException -> "Invalid Hostname"
                        is SocketTimeoutException -> "Connection Timeout"
                        else -> e.toString()
                    }
                    Log.d("chauhan",exception)
                    MyResponse.Error(exception)
                } catch (e: HttpException) {
                    val exception = when (e.code()) {
                        400 -> "Bad Request"
                        401 -> "Unauthorized"
                        404 -> "Not Found"
                        in 405..499 -> "Other Client Error"
                        in 500..599 -> "Server Error"
                        else -> "Something went wrong!!"
                    }
                    MyResponse.Error(exception)
                } catch (e: Exception) {
                    MyResponse.Error(e.localizedMessage ?: "Something went wrong!!")
                }
            } else
                MyResponse.Error("No Internet")

            trySend(emitValue)
            awaitClose { close() }
//            awaitClose()
        }
}