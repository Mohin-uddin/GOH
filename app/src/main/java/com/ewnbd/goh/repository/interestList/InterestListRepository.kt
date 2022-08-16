package com.ewnbd.goh.repository.interestList

import com.ewnbd.goh.data.model.response_all_class.interestList.InterestListResponse
import com.ewnbd.goh.ui.prefarence.InterestList
import com.ewnbd.goh.utils.Resource

interface InterestListRepository {
    suspend fun interestList(header: Map<String, String>): Resource<InterestListResponse>
}