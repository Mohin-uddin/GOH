package com.ewnbd.goh.data.model.response_all_class.chatDetails

data class Result(
    val id: Int,
    val read: Boolean,
    val `receiver`: Int,
    val sender: Int,
    val sent: String,
    val shared_profile_link: String,
    val text: String
)