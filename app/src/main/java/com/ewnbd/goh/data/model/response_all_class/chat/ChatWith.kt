package com.ewnbd.goh.data.model.response_all_class.chat

data class ChatWith(
    val date_joined: String,
    val dp: Any,
    val email: String,
    val first_name: String,
    val full_name: String,
    val groups: List<Any>,
    val id: Int,
    val is_active: Boolean,
    val is_manager: Boolean,
    val is_staff: Boolean,
    val is_superuser: Boolean,
    val last_login: String,
    val last_name: String,
    val password: String,
    val user_permissions: List<Any>,
    val username: String
)