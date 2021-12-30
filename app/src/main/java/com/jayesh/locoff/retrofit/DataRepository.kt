package com.jayesh.locoff.retrofit


import com.jayesh.locoff.callbacks.GetDataCallback
import com.jayesh.locoff.utils.OConstants

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.MutableLiveData





public class DataRepository {
    private val listOfData: MutableLiveData<ResultModel> =
        MutableLiveData<ResultModel>()
    private var dataRepository: DataRepository? = null
    fun getInstance(): DataRepository? {
        if (dataRepository == null) {
            dataRepository = DataRepository()
        }
        return dataRepository
    }



     fun getData(page:Int,callback:GetDataCallback):MutableLiveData<ResultModel>{
        val call = RetrofitClient.getInstance(OConstants.URL).myApi.getData(page)
        call.enqueue(object : Callback<ResultModel> {
            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                if(response.body()!=null && response.body()?.data!=null) {
                    listOfData.value = response.body()!!
                }else{
                    callback.onError("Error While fetching data")
                }
            }
            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                callback.onError(t.message)
            }
        })

        return listOfData
    }

}