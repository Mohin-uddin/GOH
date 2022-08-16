package com.ewnbd.goh.data.model.response_all_class.organizationParticipetorList

data class OrganizationParticipetorListResponse(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)