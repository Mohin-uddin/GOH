package com.ewnbd.goh.repository.ratingActivtyList

import com.ewnbd.goh.data.model.response_all_class.activity_rating.ActivityRatingResponse
import com.ewnbd.goh.utils.Resource

interface ActvityRatingListRepository {
    suspend fun ratingRepository(header: Map<String, String>
                                        ,activtyId: String
    ): Resource<ActivityRatingResponse>
}