package com.codewithhiren.foodorderapplatest.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.codewithhiren.foodorderapplatest.App
import com.codewithhiren.foodorderapplatest.di.ApplicationContextModule
import dagger.hilt.android.EntryPointAccessors

object HelperClass {

    private val context : Context by lazy {
        EntryPointAccessors.fromApplication(
            App(),
            ApplicationContextModule::class.java
        ).getApplicationContext()
    }

    fun isInternetAvailable(): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)

    }

    fun toMap(classObj:Any) : HashMap<String,String>{
        val map = HashMap<String,String>()
        classObj.javaClass.declaredFields.forEach {
            it.isAccessible = true
            map[it.name] = it.get(classObj)!!.toString()
        }
        return map
    }
}