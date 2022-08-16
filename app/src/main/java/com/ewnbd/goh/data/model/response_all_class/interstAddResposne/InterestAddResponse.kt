package com.ewnbd.goh.data.model.response_all_class.interstAddResposne

data class InterestAddResponse(
    val about: Any,
    val city: Any,
    val date_of_birth: Any,
    val gender: Any,
    val id: Int,
    val interests: List<Interest>,
    val language: String,
    val latitude: Any,
    val longitude: Any,
    val nickname: Any,
    val user: User
)