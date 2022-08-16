package com.ewnbd.goh.data.model.response_all_class.notificationRe

data class Data(
    val activity_date: String,
    val age_range: AgeRange,
    val category: Category,
    val created: String,
    val creator: Creator,
    val desc: String,
    val end_time: Any,
    val gender: String,
    val id: Int,
    val latitude: String,
    val longitude: String,
    val min_participate: Int,
    val name: String,
    val photo: String,
    val place: String,
    val priority: Int,
    val privacy: String,
    val start_time: String
)