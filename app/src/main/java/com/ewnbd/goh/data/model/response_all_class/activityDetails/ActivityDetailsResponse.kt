package com.ewnbd.goh.data.model.response_all_class.activityDetails

data class ActivityDetailsResponse(
    val activity_date: String,
    val activity_time: ActivityTime,
    val activity_time_id: String,
    val age_range: AgeRange,
    val age_range_id: String,
    val category: Category,
    val category_id: String,
    val created: String,
    val creator: Creator,
    val desc: String,
    val detail: String,
    val gender: String,
    val id: Int,
    val lat: Any,
    val lng: Any,
    val min_participate: Int,
    val name: String,
    val photo: String,
    val place: Any,
    val priority: Int,
    val privacy: String
)