package com.ewnbd.goh.repository.trackingList

import com.ewnbd.goh.data.model.request_all_class.ReportIssueRequest
import com.ewnbd.goh.data.model.response_all_class.issue.IssueResponse
import com.ewnbd.goh.data.model.response_all_class.trackingissue.TrackingIssueListResponse
import com.ewnbd.goh.utils.Resource

interface TrackingListRepository {
    suspend fun trackingListRequest(header: Map<String, String>
    ): Resource<TrackingIssueListResponse>
}