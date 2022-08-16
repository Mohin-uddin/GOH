package com.ewnbd.goh.repository.notifcation

import com.ewnbd.goh.data.model.response_all_class.notificationRe.NotifcationResponse
import com.ewnbd.goh.utils.Resource

interface NotificationRepository {
    suspend fun notificationResponse(header: Map<String, String>): Resource<NotifcationResponse>
}