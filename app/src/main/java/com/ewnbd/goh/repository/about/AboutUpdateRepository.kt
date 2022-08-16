package com.ewnbd.goh.repository.about

import com.ewnbd.goh.data.model.response_all_class.AboutResponse
import com.ewnbd.goh.data.model.response_all_class.acceptRequest.AcceptRequestResponse
import com.ewnbd.goh.utils.Resource

interface AboutUpdateRepository {
    suspend fun aboutUpdateRes(header : Map<String,String>,aboutUpdate: AboutResponse): Resource<AboutResponse>
}