package com.ewnbd.goh.ui.friends

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.response_all_class.friendList.FriendListResponse
import com.ewnbd.goh.data.model.response_all_class.friendRequestList.FriendRequestListresponse
import com.ewnbd.goh.data.model.response_all_class.organigationActivities.OrganizedActivitiesResponse
import com.ewnbd.goh.repository.friendList.FriendListRepository
import com.ewnbd.goh.repository.friendRequestList.FriendRequestListRepo
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
class FriendViewModel @Inject constructor(
    private val friendListRepository: FriendListRepository,
    private val friendRequestListRepo: FriendRequestListRepo,
    private var dispatcher: DispatcherProvider
) : ViewModel() {

    private var _friendListResponse = MutableStateFlow<FriendListResponse?>(null)
    var friendListResponse: StateFlow<FriendListResponse?> = _friendListResponse

    private var _friendRequestList = MutableStateFlow<FriendRequestListresponse?>(null)
    var friendRequestList: StateFlow<FriendRequestListresponse?> = _friendRequestList


    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()

    fun getFriendListRequest(header: Map<String, String>
                          ,userId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = friendListRepository.friendListResponse(header,userId)

            when(result){
                is Resource.Success -> {
                    _friendListResponse.emit(result.data)
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
    fun getFriendRequestList(header: Map<String, String>){
        viewModelScope.launch(dispatcher.io) {
            val result = friendRequestListRepo.friendRequestListResponse(header)

            when(result){
                is Resource.Success -> {
                    _friendRequestList.emit(result.data)
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
}