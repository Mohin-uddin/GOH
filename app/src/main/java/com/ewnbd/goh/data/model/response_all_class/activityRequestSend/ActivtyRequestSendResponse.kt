package com.ewnbd.goh.data.model.response_all_class.activityRequestSend

data class ActivtyRequestSendResponse(
    val activity: Activity,
    val id: Int,
    val req_sent_time: String,
    val request_sender: RequestSender
)