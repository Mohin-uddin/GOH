package com.ewnbd.goh.repository.nearActivity

import com.ewnbd.goh.data.model.response_all_class.nearActivitys.NearActivitiesResponse
import com.ewnbd.goh.data.model.response_all_class.verification.VeificationResponse
import com.ewnbd.goh.utils.Resource

interface NearActivitysListRepository {

    suspend fun nearActivityResponse(header: Map<String, String>
                                     ,latitude: String,longitude:String): Resource<NearActivitiesResponse>
}