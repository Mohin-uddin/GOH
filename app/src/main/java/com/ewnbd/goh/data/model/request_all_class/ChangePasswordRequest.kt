package com.ewnbd.goh.data.model.request_all_class

data class ChangePasswordRequest (val old_password: String, val new_password: String, val confirm_password: String)