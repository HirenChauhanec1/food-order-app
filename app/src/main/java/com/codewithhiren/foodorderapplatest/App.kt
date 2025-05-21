package com.codewithhiren.foodorderapplatest

import android.app.Application
import com.codewithhiren.foodorderapplatest.utils.NetworkStatusCheck
import com.codewithhiren.foodorderapplatest.utils.showToast
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var networkStatusCheck: NetworkStatusCheck

    override fun onCreate() {
        super.onCreate()
        observeNetwork()
    }

    private fun observeNetwork() {
        CoroutineScope(Dispatchers.IO).launch {
            networkStatusCheck.isNetworkAvailable.collect { isNetworkStatus: Boolean ->
                withContext(Dispatchers.Main) {
                    if (!isNetworkStatus)
                        showToast("Internet is not available")
                }
            }
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        networkStatusCheck.unregisterNetworkCallback()
    }

}
