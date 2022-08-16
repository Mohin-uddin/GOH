package com.ewnbd.goh.repository.notification_next

import com.ewnbd.goh.data.model.response_all_class.notificationRe.NotifcationResponse
import com.ewnbd.goh.utils.Resource

interface NotificationNextRepository {
    suspend fun notificationResponse(header: Map<String, String>,url: String): Resource<NotifcationResponse>
}