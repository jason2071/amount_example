package com.phm.myhello.utils

import android.util.Log
import com.phm.myhello.BuildConfig
import com.phm.myhello.Parameter.TAG


fun Any.log(tag: String = TAG) {
    if (!BuildConfig.DEBUG) return
    if (this is String) {
        Log.d(tag, this)
    } else {
        Log.d(tag, this.toString())
    }
}