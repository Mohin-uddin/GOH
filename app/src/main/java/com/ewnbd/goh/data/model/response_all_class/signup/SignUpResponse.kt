package com.ewnbd.goh.data.model.response_all_class.signup

data class SignUpResponse(
    val code: String,
    val email: List<String>,
    val password: List<String>,
    val token: String,
    val user: User,
    val username: List<String>,
    val verification_message: String,

)