package com.jayesh.locoff.callbacks

import com.jayesh.locoff.retrofit.ResultModel


interface GetDataCallback {
    fun onSuccess(response: ResultModel?)
    fun onError(error: String?)
}



