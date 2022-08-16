package com.ewnbd.goh.repository.organization

import com.ewnbd.goh.data.model.response_all_class.notification.SentFriendRequestResponse
import com.ewnbd.goh.data.model.response_all_class.organigationActivities.OrganizedActivitiesResponse
import com.ewnbd.goh.utils.Resource

interface OrganizationRepository {
    suspend fun organizationRequestRepo(header: Map<String, String>
                                      ,userId:String): Resource<OrganizedActivitiesResponse>
}