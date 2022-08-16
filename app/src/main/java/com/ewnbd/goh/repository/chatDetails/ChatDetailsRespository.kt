package com.ewnbd.goh.repository.chatDetails

import com.ewnbd.goh.data.model.response_all_class.chatDetails.ChatDetailsResponse
import com.ewnbd.goh.utils.Resource

interface ChatDetailsRespository {
    suspend fun chatRepository(header: Map<String, String>
                                        ,chatId : String): Resource<ChatDetailsResponse>
}