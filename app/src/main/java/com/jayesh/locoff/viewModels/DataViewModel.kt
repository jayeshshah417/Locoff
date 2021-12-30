package com.jayesh.locoff.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jayesh.locoff.retrofit.DataRepository
import com.jayesh.locoff.retrofit.ResultModel
import com.jayesh.locoff.callbacks.GetDataCallback


class DataViewModel(application: Application) :
    AndroidViewModel(application) {
    private val repository: DataRepository

    public fun getData(page: Int,callback: GetDataCallback): MutableLiveData<ResultModel> {
        val dataReturned = repository.getData(page, callback)
        return dataReturned
    }

    init {
        repository = DataRepository()
    }
}