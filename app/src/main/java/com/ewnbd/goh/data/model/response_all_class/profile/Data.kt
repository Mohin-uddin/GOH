package com.ewnbd.goh.data.model.response_all_class.profile

data class Data(
    val status: Int,
    val about: String,
    val city: String,
    val date_of_birth: String,
    val gender: String,
    val id: Int,
    val nickname: String,
    val interests: List<Interest>,
    val language: String,
    val user: User
)