package com.ewnbd.goh.data.model.response_all_class.notificationRe

data class NotifcationResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)