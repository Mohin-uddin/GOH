package com.ewnbd.goh.data.model.response_all_class.actvityDetails

data class ActvityDetailsResponse(
    val activity_date: String,
    val age_range: AgeRange,
    val age_range_id: String,
    val category: Category,
    val category_id: String,
    val created: String,
    val creator: Creator,
    val desc: String,
    val end_time: String,
    val gender: String,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val min_participate: Int,
    val name: String,
    val photo: String,
    val place: String,
    val priority: Int,
    val privacy: String,
    val start_time: String,
    val status: Int
)