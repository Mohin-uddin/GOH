package com.ewnbd.goh.repository.updateActvity

import com.ewnbd.goh.data.model.response_all_class.createActivty.CreateActivityResponse
import com.ewnbd.goh.utils.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UpdateActvityRepository  {

    suspend fun updateActivityResponse(header: Map<String, String>,
                                       dp: MultipartBody.Part,
                                       name: RequestBody,
                                       desc: RequestBody,
                                       min_participate: RequestBody,
                                       gender: RequestBody,
                                       privacy: RequestBody,
                                       latitude: RequestBody,
                                       longitude: RequestBody,
                                       place: RequestBody,
                                       activity_date: RequestBody,
                                       category_id: RequestBody,
                                       age_range_id: RequestBody,
                                       start_time: RequestBody,
                                       end_time: RequestBody,
                                       userId: String): Resource<CreateActivityResponse>
}