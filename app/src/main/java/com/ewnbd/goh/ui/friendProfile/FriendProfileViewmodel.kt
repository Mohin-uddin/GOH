package com.ewnbd.goh.ui.friendProfile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.response_all_class.acceptRequest.AcceptRequestResponse
import com.ewnbd.goh.data.model.response_all_class.friendList.FriendListResponse
import com.ewnbd.goh.data.model.response_all_class.friendRequestList.FriendRequestListresponse
import com.ewnbd.goh.data.model.response_all_class.friendRequestWithdraw.FriendRequestWithdrawResponse
import com.ewnbd.goh.data.model.response_all_class.notification.SentFriendRequestResponse
import com.ewnbd.goh.data.model.response_all_class.organigationActivities.OrganizedActivitiesResponse
import com.ewnbd.goh.data.model.response_all_class.profile.ProfileDetailsResponse
import com.ewnbd.goh.repository.acceptFreindRequest.AcceptFriendRequestRepo
import com.ewnbd.goh.repository.friendList.FriendListRepository
import com.ewnbd.goh.repository.friendRemove.FriendRemoveRepository
import com.ewnbd.goh.repository.friendRequestList.FriendRequestListRepo
import com.ewnbd.goh.repository.friendRequestRemove.FriendRequestRemoveRepo
import com.ewnbd.goh.repository.friendRequestRemove.FriendRequestRemoveRepoImp
import com.ewnbd.goh.repository.organization.OrganizationRepository
import com.ewnbd.goh.repository.participated.ParticipatedActivityRepo
import com.ewnbd.goh.repository.profile.ProfileRepository
import com.ewnbd.goh.repository.sentFreindRequest.SentFriendRequestRepository
import com.ewnbd.goh.utils.DispatcherProvider
import com.ewnbd.goh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendProfileViewmodel@Inject constructor(
    private val profileRepository: ProfileRepository,
    private val sentFriendRequestRepository: SentFriendRequestRepository,
    private val organizationRepository: OrganizationRepository,
    private val participatedActivityRepo: ParticipatedActivityRepo,
    private val friendListRepository: FriendListRepository,
    private val friendRequestListRepo: FriendRequestListRepo,
    private val friendRequestRemoveRepo: FriendRequestRemoveRepo,
    private val acceptFriendRequestRepo: AcceptFriendRequestRepo,
    private val friendRemoveRepository: FriendRemoveRepository,
    private var dispatcher: DispatcherProvider
) : ViewModel() {
    private var _profileResponse = MutableStateFlow<ProfileDetailsResponse?>(null)
    var profileResponse: StateFlow<ProfileDetailsResponse?> = _profileResponse

    private var _sentRequestResponse = MutableStateFlow<SentFriendRequestResponse?>(null)
    var sentRequestResponse : StateFlow<SentFriendRequestResponse?> = _sentRequestResponse

    private var _organizationActivityResponse = MutableStateFlow<OrganizedActivitiesResponse?>(null)
    var organizationActivityResponse: StateFlow<OrganizedActivitiesResponse?> = _organizationActivityResponse

    private var _participatedActivityResponse = MutableStateFlow<OrganizedActivitiesResponse?>(null)
    var participatedActivityResponse: StateFlow<OrganizedActivitiesResponse?> = _participatedActivityResponse

    private var _friendListResponse = MutableStateFlow<FriendListResponse?>(null)
    var friendListResponse: StateFlow<FriendListResponse?> = _friendListResponse

    private var _friendRequestList = MutableStateFlow<FriendRequestListresponse?>(null)
    var friendRequestList: StateFlow<FriendRequestListresponse?> = _friendRequestList

    private var _friendRequestRemove = MutableStateFlow<FriendRequestWithdrawResponse?>(null)
    var friendRequestRemove: StateFlow<FriendRequestWithdrawResponse?> = _friendRequestRemove

    private var _friendRequestAccept = MutableStateFlow<AcceptRequestResponse?>(null)
    var friendRequestAccept: StateFlow<AcceptRequestResponse?> = _friendRequestAccept


    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()
    fun getProfileRequest(header: Map<String, String>
                          ,userId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = profileRepository.profileResponse(header,userId)

            when(result){
                is Resource.Success -> {
                    _profileResponse.emit(result.data)
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

    fun friendRequestRemove(header: Map<String, String>
                            ,userId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = friendRequestRemoveRepo.friendRequestRemoveResponse(header,userId)

            when(result){
                is Resource.Success -> {
                    _friendRequestRemove.emit(result.data)
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

    fun friendRemove(header: Map<String, String>
                            ,userId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = friendRemoveRepository.friendRemoveResponse(header,userId)

            when(result){
                is Resource.Success -> {
                    _friendRequestRemove.emit(result.data)
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
    fun friendRequestAccept(header: Map<String, String>
                            ,userId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = acceptFriendRequestRepo.activityListResponse(header,userId)

            when(result){
                is Resource.Success -> {
                    _friendRequestAccept.emit(result.data)
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

    fun sentProfileRequest(header: Map<String, String>
                          ,userId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = sentFriendRequestRepository.sentFriendRequestRepo(header,userId)

            when(result){
                is Resource.Success -> {
                    _sentRequestResponse.emit(result.data)
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

    fun getOrganizationRequest(header: Map<String, String>
                               ,userId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = organizationRepository.organizationRequestRepo(header,userId)

            when(result){
                is Resource.Success -> {
                    _organizationActivityResponse.emit(result.data)
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

    fun getParticipatedByRequest(header: Map<String, String>
                                 ,userId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = participatedActivityRepo.participatedRequestRepo(header,userId)

            when(result){
                is Resource.Success -> {
                    _participatedActivityResponse.emit(result.data)
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

    fun getFriendList(header: Map<String, String>
                      ,userId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = friendListRepository.friendListResponse(header,userId)

            when(result){
                is Resource.Success -> {
                    _friendListResponse.emit(result.data)
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
    fun getFriendRequestList(header: Map<String, String>
                      ){
        viewModelScope.launch(dispatcher.io) {
            val result = friendRequestListRepo.friendRequestListResponse(header)

            when(result){
                is Resource.Success -> {
                    _friendRequestList.emit(result.data)
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