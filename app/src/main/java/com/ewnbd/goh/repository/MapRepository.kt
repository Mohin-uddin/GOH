package com.ewnbd.goh.repository

import com.ewnbd.goh.data.model.response_all_class.mapSearch.MapSearchResponse
import com.ewnbd.goh.data.model.response_all_class.verification.VeificationResponse
import com.ewnbd.goh.utils.Resource

interface MapRepository {
    suspend fun mapResponse(mapApi: String,searchData:String): Resource<MapSearchResponse>
}