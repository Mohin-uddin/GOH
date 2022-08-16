package com.ewnbd.goh.data.model.response_all_class.forgetpassword

data class ForgetPasswordResponse(
    val code: String,
    val username: String,
    val verification_message: String
)