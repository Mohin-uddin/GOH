package com.ewnbd.goh.data.model.response_all_class.activity_rating

data class Rating(
    val anonymous_rate: Boolean,
    val comment: String,
    val created: String,
    val rater: Rater
)