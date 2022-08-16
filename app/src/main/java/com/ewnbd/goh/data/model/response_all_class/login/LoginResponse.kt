package com.ewnbd.goh.data.model.response_all_class.login

data class LoginResponse(
    val expiry: String,
    val non_field_errors: List<String>,
    val token: String,
    val user_id: String
)