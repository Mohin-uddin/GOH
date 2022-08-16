package com.ewnbd.goh.repository.interestAdd

import com.ewnbd.goh.data.model.request_all_class.InterserAddRequest
import com.ewnbd.goh.data.model.response_all_class.interstAddResposne.InterestAddResponse
import com.ewnbd.goh.data.model.response_all_class.nearActivitys.NearActivitiesResponse
import com.ewnbd.goh.utils.Resource

interface InterestAddRepository {
    suspend fun interestAddResponse(header: Map<String, String>
                                     ,userId: String,interserAddRequest: InterserAddRequest
    ): Resource<InterestAddResponse>
}