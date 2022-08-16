package com.ewnbd.goh.ui.createActivity.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.response_all_class.actvityDetails.ActvityDetailsResponse
import com.ewnbd.goh.data.model.response_all_class.age.AgeListResponse
import com.ewnbd.goh.data.model.response_all_class.codeMatch.CodeMatchResponse
import com.ewnbd.goh.data.model.response_all_class.createActivty.CreateActivityResponse
import com.ewnbd.goh.data.model.response_all_class.timeListResponse.TimeListResponse
import com.ewnbd.goh.repository.actvityDetails.ActvityDetailsRepository
import com.ewnbd.goh.repository.age.AgeListRepository
import com.ewnbd.goh.repository.codeMtach.CodeMatchRepository
import com.ewnbd.goh.repository.createActivity.CreateActivityRepository
import com.ewnbd.goh.repository.forgetpassword.recetCodeGenerate.RecetCodeGenerateRepository
import com.ewnbd.goh.repository.timeList.TimeListRepository
import com.ewnbd.goh.repository.updateActvity.UpdateActvityRepository
import com.ewnbd.goh.utils.DispatcherProvider
import com.ewnbd.goh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class CreateActivityViewModel @Inject constructor(
    private var createActivityRepository: CreateActivityRepository,
    private var timeListRepository : TimeListRepository,
    private var ageLisRepository: AgeListRepository,
    private var actvityDetailsRepository: ActvityDetailsRepository,
    private var updateActvityRepository: UpdateActvityRepository,
    private var dispatcher: DispatcherProvider
): ViewModel() {
    private var _createActivityResponse = MutableStateFlow<CreateActivityResponse?>(null)
    var createActivityResponse: StateFlow<CreateActivityResponse?> = _createActivityResponse
    private var _ageListResponse = MutableStateFlow<AgeListResponse?>(null)
    var ageListResponse: StateFlow<AgeListResponse?> = _ageListResponse
    private var _timeListResponse = MutableStateFlow<TimeListResponse?>(null)
    var timeListResponse: StateFlow<TimeListResponse?> = _timeListResponse

    private var _actvitiyDetails = MutableStateFlow<ActvityDetailsResponse?>(null)
    var actvitiyDetails: StateFlow<ActvityDetailsResponse?> = _actvitiyDetails

    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()
    fun getRecentCodeGenerateRequest(header: Map<String, String>,
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
                                     end_time:RequestBody,
                                     userId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = createActivityRepository.createActivityResponse(header,dp,name, desc,
                min_participate, gender, privacy, latitude, longitude, place, activity_date,
                category_id, age_range_id,start_time,end_time , userId)

            when(result){
                is Resource.Success -> {
                    _createActivityResponse.emit(result.data)
                    Log.e("dataCheck", "getLoginRequest: asd "+result.message )
                }
                is Resource.Error -> {
                    Log.e("hlwvaliddata", "getLoginRequest:aida ni "+result.message )
                    _responseCode.emit( result.message.toString())
                    Log.e("hlwvaliddata", "getLoginRequest:aida pore "+result.message )
                }
            }
        }
    }

    fun updateActivity(header: Map<String, String>,
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
                                     end_time:RequestBody,
                                     userId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = updateActvityRepository.updateActivityResponse(header,dp,name, desc,
                min_participate, gender, privacy, latitude, longitude, place, activity_date,
                category_id, age_range_id,start_time,end_time , userId)

            when(result){
                is Resource.Success -> {
                    _createActivityResponse.emit(result.data)
                    Log.e("dataCheck", "getLoginRequest: asd "+result.message )
                }
                is Resource.Error -> {
                    Log.e("hlwvaliddata", "getLoginRequest:aida ni "+result.message )
                    _responseCode.emit( result.message.toString())
                    Log.e("hlwvaliddata", "getLoginRequest:aida pore "+result.message )
                }
            }
        }
    }

    fun getTimeRequest(header: Map<String, String>){
        viewModelScope.launch(dispatcher.io) {
            val result = timeListRepository.timeListResponse(header)

            when(result){
                is Resource.Success -> {
                    _timeListResponse.emit(result.data)
                    Log.e("dataCheckProfile", "getLoginRequest: asd "+result.data.toString() )
                }
                is Resource.Error -> {
                    Log.e("dataCheck", "getLoginRequest:aida ni "+result.message )
                    _responseCode.emit( result.message.toString())
                    Log.e("dataCheck", "getLoginRequest:aida pore "+result.message )
                }
            }
        }
    }

    fun getAgeListRequest(header: Map<String, String>){
        viewModelScope.launch(dispatcher.io) {
            val result = ageLisRepository.ageListResponse(header)

            when(result){
                is Resource.Success -> {
                    _ageListResponse.emit(result.data)
                    Log.e("dataCheckProfile", "getLoginRequest: asd "+result.data.toString() )
                }
                is Resource.Error -> {
                    Log.e("dataCheck", "getLoginRequest:aida ni "+result.message )
                    _responseCode.emit( result.message.toString())
                    Log.e("dataCheck", "getLoginRequest:aida pore "+result.message )
                }
            }
        }
    }

    fun getActvityDetailsRequest(header: Map<String, String>,actvityId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = actvityDetailsRepository.activityDetailsResponse(header,actvityId)

            when(result){
                is Resource.Success -> {
                    _actvitiyDetails.emit(result.data)
                    Log.e("dataCheckProfile", "getLoginRequest: asd "+result.data.toString() )
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
        _createActivityResponse.value=null
    }
}