package com.ewnbd.goh.ui.promotion

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.request_all_class.InterserAddRequest
import com.ewnbd.goh.data.model.request_all_class.PromotionRequest
import com.ewnbd.goh.data.model.response_all_class.mapSearch.MapSearchResponse
import com.ewnbd.goh.data.model.response_all_class.promotion.PromotionResponse
import com.ewnbd.goh.repository.MapRepository
import com.ewnbd.goh.repository.addBasicProfile.AddBasicProfileRepository
import com.ewnbd.goh.repository.interestAdd.InterestAddRepository
import com.ewnbd.goh.repository.interestList.InterestListRepository
import com.ewnbd.goh.repository.promotion.PromotionRepository
import com.ewnbd.goh.utils.DispatcherProvider
import com.ewnbd.goh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PromotionViewmodel @Inject constructor(
   private val promotionRepository: PromotionRepository,
    private var dispatcher: DispatcherProvider
): ViewModel() {

    private var _promotionData = MutableStateFlow<PromotionResponse?>(null)
    var promotionData: StateFlow<PromotionResponse?> = _promotionData

    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()


    fun getPromotionResponse(header: Map<String, String>
                       ,promotionRequest: PromotionRequest
    ){
        viewModelScope.launch(dispatcher.io) {
            val result = promotionRepository.profilePicUpdateResponse(header,promotionRequest)

            when(result){
                is Resource.Success -> {
                    _promotionData.emit(result.data)
                    Log.e("dataCheck", "getLoginRequest: asd "+result.message )
                }
                is Resource.Error -> {
                    Log.e("dataCheck", "getLoginRequest:aida ni "+result.message )
                    _responseCode.emit( result.message.toString())
                    Log.e("dataCheck", "getLoginRequest:aida pore "+result.message )
                }
            }
        }


    }
}