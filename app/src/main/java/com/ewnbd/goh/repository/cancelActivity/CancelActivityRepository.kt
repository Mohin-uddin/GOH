package com.ewnbd.goh.repository.cancelActivity

import com.ewnbd.goh.data.model.response_all_class.cancelActivity.CancelActivityResponse
import com.ewnbd.goh.data.model.response_all_class.organigationActivities.OrganizedActivitiesResponse
import com.ewnbd.goh.utils.Resource

interface CancelActivityRepository {
    suspend fun cancelActivity(header: Map<String, String>
                                        ,activtyId:String): Resource<CancelActivityResponse>
}