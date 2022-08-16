package com.ewnbd.goh.ui.filter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.request_all_class.ActivityFilterRequest
import com.ewnbd.goh.data.model.response_all_class.activityRequestSend.ActivtyRequestSendResponse
import com.ewnbd.goh.data.model.response_all_class.activity_categorylist.ActivityCategoryListResponse
import com.ewnbd.goh.data.model.response_all_class.age.AgeListResponse
import com.ewnbd.goh.data.model.response_all_class.nearActivitys.NearActivitiesResponse
import com.ewnbd.goh.data.model.response_all_class.organizationRequestList.OrganizationJoinRequestListResponse
import com.ewnbd.goh.data.model.response_all_class.timeListResponse.TimeListResponse
import com.ewnbd.goh.repository.activityFilter.ActivityFilterRepository
import com.ewnbd.goh.repository.age.AgeListRepository
import com.ewnbd.goh.repository.categories.CategoryRepository
import com.ewnbd.goh.repository.timeList.TimeListRepository
import com.ewnbd.goh.utils.DispatcherProvider
import com.ewnbd.goh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
   private val activityFilterRepository: ActivityFilterRepository,
   private val timeListRepository: TimeListRepository,
   private val categoryRepository: CategoryRepository,
   private val ageListRepository: AgeListRepository,
   private var dispatcher: DispatcherProvider
): ViewModel(){
    private var _ageListResponse = MutableStateFlow<AgeListResponse?>(null)
    var ageListResponse: StateFlow<AgeListResponse?> = _ageListResponse
    private var _timeListResponse = MutableStateFlow<TimeListResponse?>(null)
    var timeListResponse: StateFlow<TimeListResponse?> = _timeListResponse

    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()

    private var _categoryResponse = MutableStateFlow<ActivityCategoryListResponse?>(null)
    var categoryResponse: StateFlow<ActivityCategoryListResponse?> = _categoryResponse

    private var _filterResponse = MutableStateFlow<NearActivitiesResponse?>(null)
    var filterResponse: StateFlow<NearActivitiesResponse?> = _filterResponse
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
            val result = ageListRepository.ageListResponse(header)

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


    fun getCategoryByRequest(header: Map<String, String>){
        viewModelScope.launch(dispatcher.io) {
            val result = categoryRepository.categoryResponse(header)

            when(result){
                is Resource.Success -> {
                    _categoryResponse.emit(result.data)
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

    fun getFilterByRequest(header: Map<String, String>,filterRequest: ActivityFilterRequest){
        viewModelScope.launch(dispatcher.io) {
            val result = activityFilterRepository.activityFilterResponse(header,filterRequest)

            when(result){
                is Resource.Success -> {
                    _filterResponse.emit(result.data)
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
        _filterResponse.value=null
        _responseCode.value="0"
    }
}