package com.jayesh.locoff.retrofit

import java.io.Serializable

data class ResultModel (var per_page: Int? = null, var page: Int? = null,var total: Int? = null,var total_pages:Int?,
                                 var data: MutableList<DataModel>? = null,var support: SupportModel? = null ): Serializable
data class DataModel(var first_name: String?,var last_name: String?,var email: String?,var id: Int?,var avatar: String?)
data class SupportModel(var url: String?,var text:String?)

