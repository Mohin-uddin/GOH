package com.ewnbd.goh.data.model.request_all_class

data class ActivityFilterRequest (val category_id:String,val gender: String,val activity_date: String,
                                  val latitude: String,val longitude:String,val activity_time_id: Int,val age_range_id: Int)