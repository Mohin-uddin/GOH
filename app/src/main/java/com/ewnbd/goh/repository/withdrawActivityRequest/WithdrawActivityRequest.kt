package com.ewnbd.goh.repository.withdrawActivityRequest

import com.ewnbd.goh.data.model.request_all_class.InterserAddRequest
import com.ewnbd.goh.data.model.response_all_class.WithdrawResponse
import com.ewnbd.goh.data.model.response_all_class.interstAddResposne.InterestAddResponse
import com.ewnbd.goh.utils.Resource

interface WithdrawActivityRequest {
    suspend fun withDrawRequestResponse(header: Map<String, String>
                                    ,activtyId: String
    ): Resource<WithdrawResponse>
}