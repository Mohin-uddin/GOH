package com.ewnbd.goh.repository.reportIssue

import com.ewnbd.goh.data.model.request_all_class.ReportIssueRequest
import com.ewnbd.goh.data.model.response_all_class.activity_rating.ActivityRatingResponse
import com.ewnbd.goh.data.model.response_all_class.issue.IssueResponse
import com.ewnbd.goh.utils.Resource

interface ReportissueRepository {
    suspend fun reportIssueRequest(header: Map<String, String>
                                 ,reportIssueRequest: ReportIssueRequest
    ): Resource<IssueResponse>
}