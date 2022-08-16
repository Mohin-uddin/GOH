package com.ewnbd.goh.data.model.response_all_class.notificationRe

data class Result(
    val created: String,
    val extra_data: ExtraData,
    val id: Int,
    val noti_type: String,
    val object_id: Int,
    val read: Boolean,
    val recipient: List<Recipient>,
    val sender: Sender,
    val verb: String,
    val verb_arabic: String
)