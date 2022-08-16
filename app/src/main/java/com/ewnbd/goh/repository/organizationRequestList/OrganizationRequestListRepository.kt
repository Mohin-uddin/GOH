package com.ewnbd.goh.repository.organizationRequestList

import com.ewnbd.goh.data.model.response_all_class.organigationActivities.OrganizedActivitiesResponse
import com.ewnbd.goh.data.model.response_all_class.organizationRequestList.OrganizationJoinRequestListResponse
import com.ewnbd.goh.utils.Resource

interface OrganizationRequestListRepository {
    suspend fun participatedRequestRepo(header: Map<String, String>
                                        ,activityId:String): Resource<OrganizationJoinRequestListResponse>
}