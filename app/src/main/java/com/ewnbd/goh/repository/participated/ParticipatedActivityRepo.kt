package com.ewnbd.goh.repository.participated

import com.ewnbd.goh.data.model.response_all_class.organigationActivities.OrganizedActivitiesResponse
import com.ewnbd.goh.utils.Resource

interface ParticipatedActivityRepo {
    suspend fun participatedRequestRepo(header: Map<String, String>
                                        ,userId:String): Resource<OrganizedActivitiesResponse>
}