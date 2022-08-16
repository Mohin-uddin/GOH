package com.ewnbd.goh.repository.nickName

import com.ewnbd.goh.data.model.request_all_class.UpdateNameRequest
import com.ewnbd.goh.data.model.response_all_class.NickNameUpdate
import com.ewnbd.goh.data.model.response_all_class.name_update.NameUpdateResponse
import com.ewnbd.goh.utils.Resource

interface NickNameUpdateRepository {
    suspend fun updateNameRepo(header: Map<String, String>,nickNameUpdate: NickNameUpdate):
            Resource<NickNameUpdate>
}