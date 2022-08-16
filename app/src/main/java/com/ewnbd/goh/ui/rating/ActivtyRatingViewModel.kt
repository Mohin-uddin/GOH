package com.ewnbd.goh.ui.rating

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.request_all_class.RatingActivityRequest
import com.ewnbd.goh.data.model.response_all_class.RatingPersonResponse
import com.ewnbd.goh.data.model.response_all_class.activity_rating.ActivityRatingResponse
import com.ewnbd.goh.repository.ratingActivtyList.ActvityRatingListRepository
import com.ewnbd.goh.repository.ratingPersonActivity.RatingRatingActivityRepository
import com.ewnbd.goh.utils.DispatcherProvider
import com.ewnbd.goh.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivtyRatingViewModel @Inject constructor(
    private val ratingListRepository: ActvityRatingListRepository,
    private val ratingRatingActivityRepository: RatingRatingActivityRepository,
    private var dispatcher: DispatcherProvider
) : ViewModel() {

    private var _activtyRatingList = MutableStateFlow<ActivityRatingResponse?>(null)
    var activtyRatingList: StateFlow<ActivityRatingResponse?> = _activtyRatingList

    private var _activityRatingPerson = MutableStateFlow<RatingPersonResponse?>(null)
    var activityRatingPerson: StateFlow<RatingPersonResponse?> = _activityRatingPerson

    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()
    fun ratingList(header: Map<String, String>
                        ,activtyId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = ratingListRepository.ratingRepository(header,activtyId)

            when(result){
                is Resource.Success -> {
                    _activtyRatingList.emit(result.data)
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

    fun ratingList(header: Map<String, String>
                   ,activtyId: String,ratingActivityRequest: RatingActivityRequest){
        viewModelScope.launch(dispatcher.io) {
            val result = ratingRatingActivityRepository.ratingPersonRepository(header,activtyId,ratingActivityRequest)

            when(result){
                is Resource.Success -> {
                    _activityRatingPerson.emit(result.data)
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

    fun getDataClear(){
        _responseCode.value="0"
    }
}