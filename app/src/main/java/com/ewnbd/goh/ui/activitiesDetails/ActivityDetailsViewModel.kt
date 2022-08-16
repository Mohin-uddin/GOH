package com.ewnbd.goh.ui.activitiesDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.response_all_class.ShareActivityResponse
import com.ewnbd.goh.data.model.response_all_class.acceptActivityRequest.ActivityAcceptRequestResponse
import com.ewnbd.goh.data.model.response_all_class.activityRequestSend.ActivtyRequestSendResponse
import com.ewnbd.goh.data.model.response_all_class.cancelActivity.CancelActivityResponse
import com.ewnbd.goh.data.model.response_all_class.organizationParticipetorList.OrganizationParticipetorListResponse
import com.ewnbd.goh.data.model.response_all_class.organizationRequestList.OrganizationJoinRequestListResponse
import com.ewnbd.goh.data.model.response_all_class.profile.ProfileDetailsResponse
import com.ewnbd.goh.repository.acceptRequestActivity.AcceptActivityRequestRepo
import com.ewnbd.goh.repository.activityRequestCancel.ActivityRequestCancelRepository
import com.ewnbd.goh.repository.activityRequestSent.ActivitySentRequestRepository
import com.ewnbd.goh.repository.cancelActivity.CancelActivityRepository
import com.ewnbd.goh.repository.organization.OrganizationRepository
import com.ewnbd.goh.repository.organizationParticipetor.OrganizationParticipetorRepo
import com.ewnbd.goh.repository.organizationRequestList.OrganizationRequestListRepository
import com.ewnbd.goh.repository.participated.ParticipatedActivityRepo
import com.ewnbd.goh.repository.participatorDelete.ParticipatorDeleteRepository
import com.ewnbd.goh.utils.DispatcherProvider
import com.ewnbd.goh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityDetailsViewModel @Inject constructor(
    private var activitySentRequestRepository: ActivitySentRequestRepository,
    private var organizationRequestListRepository: OrganizationRequestListRepository,
    private var acceptActivityRequestRepo: AcceptActivityRequestRepo,
    private var cancelActivityRepository: CancelActivityRepository,
    private var organizationParticipetorRepo: OrganizationParticipetorRepo,
    private var activityRequestCancelRepository: ActivityRequestCancelRepository,
    private var participatorDeleteRepository: ParticipatorDeleteRepository,
    private var dispatcher: DispatcherProvider
): ViewModel() {

    private var _activityRequestResponse = MutableStateFlow<ActivtyRequestSendResponse?>(null)
    var activityRequestResponse: StateFlow<ActivtyRequestSendResponse?> = _activityRequestResponse

    private var _participator = MutableStateFlow<ShareActivityResponse?>(null)
    var participator: StateFlow<ShareActivityResponse?> = _participator

    private var _activityCacnelResponse = MutableStateFlow<CancelActivityResponse?>(null)
    var activityCacnelResponse: StateFlow<CancelActivityResponse?> = _activityCacnelResponse

    private var _activityRequestCacnelResponse = MutableStateFlow<ShareActivityResponse?>(null)
    var activityRequestCacnelResponse: StateFlow<ShareActivityResponse?> = _activityRequestCacnelResponse

    private var _activityAcceptResponse = MutableStateFlow<ActivityAcceptRequestResponse?>(null)
    var activityAcceptResponse: StateFlow<ActivityAcceptRequestResponse?> = _activityAcceptResponse

    private var _organizationParticepetorResponse = MutableStateFlow<OrganizationParticipetorListResponse?>(null)
    var organizationParticepetorResponse: StateFlow<OrganizationParticipetorListResponse?> = _organizationParticepetorResponse

    private var _organizationRequestResponse = MutableStateFlow<OrganizationJoinRequestListResponse?>(null)
    var organizationRequestResponse: StateFlow<OrganizationJoinRequestListResponse?> = _organizationRequestResponse

    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()
    fun getProfileRequest(header: Map<String, String>
                          ,activityId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = activitySentRequestRepository.activitySendRequestResponse(header,activityId)

            when(result){
                is Resource.Success -> {
                    _activityRequestResponse.emit(result.data)
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

    fun getOrganizationRequestListRequest(header: Map<String, String>
                          ,activityId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = organizationRequestListRepository.participatedRequestRepo(header,activityId)

            when(result){
                is Resource.Success -> {
                    _organizationRequestResponse.emit(result.data)
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
    fun getActivityCancelRequest(header: Map<String, String>
                                          ,activityId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = cancelActivityRepository.cancelActivity(header,activityId)

            when(result){
                is Resource.Success -> {
                    _activityCacnelResponse.emit(result.data)
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

    fun getActivityRequestAccept(header: Map<String, String>
                                 ,activityId: String,userId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = acceptActivityRequestRepo.activityAcceptResponse(header,activityId,userId)

            when(result){
                is Resource.Success -> {
                    _activityAcceptResponse.emit(result.data)
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

    fun getActivityRequestCancel(header: Map<String, String>
                                 ,activityId: String,userId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = activityRequestCancelRepository.activityRequestDenyResponse(header,userId,activityId)

            when(result){
                is Resource.Success -> {
                    _activityRequestCacnelResponse.emit(result.data)
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

    fun getParticipatorCancel(header: Map<String, String>
                                 ,participatorId: String,actvityId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = participatorDeleteRepository.participatorResponse(header,participatorId,actvityId)

            when(result){
                is Resource.Success -> {
                    _participator.emit(result.data)
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

    fun getOrganizationParticepetor(header: Map<String, String>
                                 ,userId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = organizationParticipetorRepo.organizationParticipetorRepo(header,userId)

            when(result){
                is Resource.Success -> {
                    _organizationParticepetorResponse.emit(result.data)
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
    }
}