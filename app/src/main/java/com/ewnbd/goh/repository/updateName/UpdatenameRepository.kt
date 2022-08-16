package com.ewnbd.goh.repository.updateName

import com.ewnbd.goh.data.model.request_all_class.UpdateNameRequest
import com.ewnbd.goh.data.model.response_all_class.name_update.NameUpdateResponse
import com.ewnbd.goh.data.model.response_all_class.notification.SentFriendRequestResponse
import com.ewnbd.goh.utils.Resource

interface UpdatenameRepository {
    suspend fun updateNameRepo(header: Map<String, String>,updateNameRequest: UpdateNameRequest):
            Resource<NameUpdateResponse>
}