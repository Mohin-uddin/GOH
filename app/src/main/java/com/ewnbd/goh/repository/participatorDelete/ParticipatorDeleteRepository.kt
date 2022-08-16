package com.ewnbd.goh.repository.participatorDelete

import com.ewnbd.goh.data.model.response_all_class.ShareActivityResponse
import com.ewnbd.goh.data.model.response_all_class.notificationRe.NotifcationResponse
import com.ewnbd.goh.utils.Resource

interface ParticipatorDeleteRepository {
    suspend fun participatorResponse(header: Map<String, String>,participator: String,
                                     activityID: String): Resource<ShareActivityResponse>
}