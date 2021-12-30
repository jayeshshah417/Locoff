package com.jayesh.locoff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jayesh.locoff.retrofit.DataModel
import com.jayesh.locoff.retrofit.ResultModel
import com.jayesh.locoff.adapter.DataRecyclerAdapter
import com.jayesh.locoff.callbacks.GetDataCallback
import com.jayesh.locoff.viewModels.DataViewModel
import android.widget.Toast




class MainActivity : AppCompatActivity(), GetDataCallback, DataRecyclerAdapter.OnClickUser {

    private lateinit var contextMainActivity:MainActivity
    private lateinit var dataViewModel: DataViewModel
    var dataLists: MutableList<DataModel> = ArrayList()
    private lateinit var nsv:NestedScrollView
    private lateinit var rv_data:RecyclerView
    private lateinit var dataRecyclerAdapter:DataRecyclerAdapter
    private lateinit var ll_progress:LinearLayout
    private var page = 1
    private var checkIfDataLessThanScreenSize    = false
    private lateinit var dataResultModel:ResultModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setViews()
        contextMainActivity = this

        dataViewModel.getData(page,this).observe(this,Observer{
            dataResultModel = it
            dataLists.addAll(it.data!!)
            ll_progress.visibility=View.GONE
            dataRecyclerAdapter.setData(dataLists)
            dataRecyclerAdapter.notifyDataSetChanged()
        })
        nsv.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                page++
                ll_progress.visibility=View.VISIBLE
                if(dataResultModel==null || (dataResultModel!=null && dataResultModel.total_pages!! >=page)) {
                    dataViewModel.getData(page, this)
                }
            }
        })

        rv_data.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if(!checkIfDataLessThanScreenSize){
                        checkIfDataLessThanScreenSize=true
                        page++
                        if(dataResultModel==null || (dataResultModel!=null && dataResultModel.total_pages!! >page)) {
                            dataViewModel.getData(page, contextMainActivity)
                            ll_progress.visibility = View.VISIBLE
                            rv_data.removeOnScrollListener(this)
                        }
                    }
                    Toast.makeText(applicationContext, "Last", Toast.LENGTH_LONG).show()
                }
            }
        })

    }

    private fun setViews() {
        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        dataRecyclerAdapter = DataRecyclerAdapter(ArrayList<DataModel>(),this,this)
        nsv = findViewById(R.id.nsv)
        rv_data = findViewById(R.id.rv_data)
        ll_progress = findViewById(R.id.ll_progress)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.VERTICAL,
            false
        )
        rv_data.setLayoutManager(layoutManager)
        rv_data.setAdapter(dataRecyclerAdapter)
        rv_data.setItemAnimator(DefaultItemAnimator())
    }

    override fun onSuccess(response: ResultModel?) {
        Log.e("response",response.toString())
    }

    override fun onError(error: String?) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show()
    }

    override fun onClickUser(dataModel: DataModel) {
        Toast.makeText(this,dataModel.first_name+" "+dataModel.last_name,Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        page=1
        super.onResume()
    }
}