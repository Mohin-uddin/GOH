package com.ewnbd.goh.repository.caht

import com.ewnbd.goh.data.model.response_all_class.chat.ChatListResponse
import com.ewnbd.goh.utils.Resource

interface ChatRepository {
    suspend fun chatRepository(header: Map<String, String>
    ): Resource<ChatListResponse>
}