package com.ewnbd.goh.data.model.response_all_class.basicProfile

data class BasicProfileResponse(
    val about: String,
    val city: String,
    val date_of_birth: String,
    val gender: String,
    val id: Int,
    val interests: List<Any>,
    val language: String,
    val user: User
)