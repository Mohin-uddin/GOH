package com.ewnbd.goh.repository.categoryWiseSearch

import com.ewnbd.goh.data.model.response_all_class.nearActivitys.NearActivitiesResponse
import com.ewnbd.goh.data.model.response_all_class.organigationActivities.OrganizedActivitiesResponse
import com.ewnbd.goh.utils.Resource

interface CategoryWiseActivitySearchRepo {
    suspend fun categorySearchRequestRepo(header: Map<String, String>
                                        ,categoryId:String): Resource<NearActivitiesResponse>
}