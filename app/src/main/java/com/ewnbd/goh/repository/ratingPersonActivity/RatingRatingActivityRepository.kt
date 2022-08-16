package com.ewnbd.goh.repository.ratingPersonActivity

import com.ewnbd.goh.data.model.request_all_class.RatingActivityRequest
import com.ewnbd.goh.data.model.response_all_class.RatingPersonResponse
import com.ewnbd.goh.data.model.response_all_class.activity_rating.ActivityRatingResponse
import com.ewnbd.goh.utils.Resource

interface RatingRatingActivityRepository {
    suspend fun ratingPersonRepository(header: Map<String, String>
                                 ,activtyId: String,ratingActivityRequest: RatingActivityRequest
    ): Resource<RatingPersonResponse>
}