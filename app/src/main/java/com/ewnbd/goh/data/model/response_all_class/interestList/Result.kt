package com.ewnbd.goh.data.model.response_all_class.interestList

data class Result(
    val created: String,
    val id: Int,
    val interest_type: String,
    val interest_type_ar: String,
    var state: Boolean = false
)