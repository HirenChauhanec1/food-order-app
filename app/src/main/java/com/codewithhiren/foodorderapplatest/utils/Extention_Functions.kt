package com.codewithhiren.foodorderapplatest.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(msg:String,duration: Int = Toast.LENGTH_LONG){
    Toast.makeText(requireContext(),msg,duration).show()
}

fun Context.showToast(msg:String,duration: Int = Toast.LENGTH_LONG){
    Toast.makeText(this,msg,duration).show()
}


