package com.ewnbd.goh.data.model.response_all_class.acceptRequest

data class AcceptRequestResponse(
    val fr_creator: FrCreator,
    val friendship_time: String,
    val id: Int,
    val other_user: OtherUser
)