package com.ewnbd.goh.ui.fragment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.response_all_class.ShareActivityResponse
import com.ewnbd.goh.data.model.response_all_class.WithdrawResponse
import com.ewnbd.goh.data.model.response_all_class.activity_categorylist.ActivityCategoryListResponse
import com.ewnbd.goh.data.model.response_all_class.nearActivitys.NearActivitiesResponse
import com.ewnbd.goh.repository.activityShare.ActivityShareRepository
import com.ewnbd.goh.repository.categories.CategoryRepository
import com.ewnbd.goh.repository.nearActivity.NearActivitysListRepository
import com.ewnbd.goh.repository.withdrawActivityRequest.WithdrawActivityRequest
import com.ewnbd.goh.utils.DispatcherProvider
import com.ewnbd.goh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearActivityListViewModel @Inject constructor(
    private val nearActivitysListRepository: NearActivitysListRepository,
    private val withdrawActivityRequest: WithdrawActivityRequest,
    private val activityShareRepository: ActivityShareRepository,
    private val categoryRepository: CategoryRepository,
    private var dispatcher: DispatcherProvider
): ViewModel() {

    private var _withdrawRequest = MutableStateFlow<WithdrawResponse?>(null)
    var withdrawRequest: StateFlow<WithdrawResponse?> = _withdrawRequest
    private var _nearByActivityResponse = MutableStateFlow<NearActivitiesResponse?>(null)
    var nearByActivityResponse: StateFlow<NearActivitiesResponse?> = _nearByActivityResponse

    private var _categoryResponse = MutableStateFlow<ActivityCategoryListResponse?>(null)
    var categoryResponse: StateFlow<ActivityCategoryListResponse?> = _categoryResponse

    private var _notificationData = MutableStateFlow<Int>(0)
    var notificationData: StateFlow<Int> = _notificationData

    private var _activityShare = MutableStateFlow<ShareActivityResponse?>(null)
    var activityShare: StateFlow<ShareActivityResponse?> = _activityShare

    private var _notificationUpdate = MutableStateFlow<String>("")
    var notificationUpdate: StateFlow<String> =_notificationUpdate.asStateFlow()

    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()
    fun getNearByRequest(header: Map<String, String>
                        ,latitude: String,longitude:String){
        viewModelScope.launch(dispatcher.io) {
            val result = nearActivitysListRepository.nearActivityResponse(header,latitude,longitude)

            when(result){
                is Resource.Success -> {
                    _nearByActivityResponse.emit(result.data)
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

    fun getWithdrawRequest(header: Map<String, String>
                         ,activtyId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = withdrawActivityRequest.withDrawRequestResponse(header,activtyId)

            when(result){
                is Resource.Success -> {
                    _withdrawRequest.emit(result.data)
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

    fun getActivityShare(header: Map<String, String>
                           ,userId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = activityShareRepository.activityShareResponse(header,userId)

            when(result){
                is Resource.Success -> {
                    _activityShare.emit(result.data)
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

    fun dataUpdateNotification(string: String){
        _notificationUpdate.value=string
    }

    fun getNotificationCount (count: Int){
        viewModelScope.launch(dispatcher.main) {
            _notificationData.value = count
        }

    }
    fun getDataClear(){
        _responseCode.value="0"
    }
}