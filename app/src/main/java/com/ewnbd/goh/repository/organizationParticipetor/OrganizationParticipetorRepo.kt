package com.ewnbd.goh.repository.organizationParticipetor

import com.ewnbd.goh.data.model.response_all_class.organizationParticipetorList.OrganizationParticipetorListResponse
import com.ewnbd.goh.utils.Resource

interface OrganizationParticipetorRepo {
    suspend fun organizationParticipetorRepo(header: Map<String, String>
                                        ,userId:String): Resource<OrganizationParticipetorListResponse>
}