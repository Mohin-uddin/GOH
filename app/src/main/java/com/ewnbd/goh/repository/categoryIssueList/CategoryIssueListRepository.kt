package com.ewnbd.goh.repository.categoryIssueList

import com.ewnbd.goh.data.model.response_all_class.CategoryIssueListResponse
import com.ewnbd.goh.data.model.response_all_class.activity_categorylist.ActivityCategoryListResponse
import com.ewnbd.goh.utils.Resource

interface CategoryIssueListRepository {
    suspend fun categoryIssueResponse(header: Map<String, String>): Resource<CategoryIssueListResponse>
}