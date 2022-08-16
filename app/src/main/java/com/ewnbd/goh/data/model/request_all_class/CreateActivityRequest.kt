package com.ewnbd.goh.data.model.request_all_class

data class CreateActivityRequest(val name: String,val desc: String,val min_participate: Int,
                                  val gender:String,val privacy: String,val latitude: Double,
                                  val longitude: Double,val activity_date: String,val category_id:Int,
                                  val age_range_id: Int,val activity_time_id:Int )