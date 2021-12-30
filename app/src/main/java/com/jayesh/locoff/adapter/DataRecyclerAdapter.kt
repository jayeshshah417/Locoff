package com.jayesh.locoff.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.jayesh.locoff.R
import com.jayesh.locoff.retrofit.DataModel

import com.jayesh.locoff.utils.CircleTransform

import com.squareup.picasso.Picasso





internal class DataRecyclerAdapter(private var dataList: MutableList<DataModel>, private var context:Context, var callback:OnClickUser):
    RecyclerView.Adapter<DataRecyclerAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View, callback: OnClickUser) : RecyclerView.ViewHolder(view){

        var name: TextView = view.findViewById(R.id.tv_first_name)
        var last_name: TextView = view.findViewById(R.id.tv_last_name)
        var email: TextView = view.findViewById(R.id.tv_email)
        var avatar: ImageView = view.findViewById(R.id.iv_avatar)


        init {
            view.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    callback.onClickUser(dataList[adapterPosition])
                }

            })
        }
    }

    interface OnClickUser{
        abstract fun onClickUser(dataModel: DataModel)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.data_list_item_layout, parent, false)
        return MyViewHolder(itemView,callback)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataItem = dataList[position]
        holder.name.text=dataItem.first_name?.uppercase()
        holder.last_name.text=dataItem.last_name?.uppercase()
        holder.email.text=dataItem.email?.uppercase()

        Picasso.with(context).load(dataItem.avatar).transform(CircleTransform()).into(holder.avatar)
    }
    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(mDataList:MutableList<DataModel>){
        this.dataList = mDataList
    }
    fun getData():MutableList<DataModel>{
        return this.dataList
    }

}