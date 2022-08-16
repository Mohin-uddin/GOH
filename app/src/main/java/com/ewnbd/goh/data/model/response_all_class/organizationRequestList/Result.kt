package com.ewnbd.goh.data.model.response_all_class.organizationRequestList

data class Result(
    val activity: Activity,
    val id: Int,
    val req_sent_time: String,
    val request_sender: RequestSender
)