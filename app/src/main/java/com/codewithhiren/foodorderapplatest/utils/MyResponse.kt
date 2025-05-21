package com.codewithhiren.foodorderapplatest.utils


sealed class MyResponse<T> {
    data class Success<T>(val data: T) : MyResponse<T>()
    data class Error<T>(val errorMsg: String) : MyResponse<T>()
    class Loading<T> : MyResponse<T>()
}

