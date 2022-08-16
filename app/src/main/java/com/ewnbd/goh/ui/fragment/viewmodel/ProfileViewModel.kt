package com.ewnbd.goh.ui.fragment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.response_all_class.AboutResponse
import com.ewnbd.goh.data.model.response_all_class.NickNameUpdate
import com.ewnbd.goh.data.model.response_all_class.profile.ProfileDetailsResponse
import com.ewnbd.goh.repository.about.AboutUpdateRepository
import com.ewnbd.goh.repository.nickName.NickNameUpdateRepository
import com.ewnbd.goh.repository.profile.ProfileRepository
import com.ewnbd.goh.utils.DispatcherProvider
import com.ewnbd.goh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private var dispatcher: DispatcherProvider,
    private val nickNameUpdateRepository: NickNameUpdateRepository,
    private var aboutUpdateRepository: AboutUpdateRepository
): ViewModel() {
    private var _profileResponse = MutableStateFlow<ProfileDetailsResponse?>(null)
    var profileResponse: StateFlow<ProfileDetailsResponse?> = _profileResponse

    private var _aboutUpdate = MutableStateFlow<AboutResponse?>(null)
    var aboutUpdate: StateFlow<AboutResponse?> = _aboutUpdate

    private var _nickNameUpdate = MutableStateFlow<NickNameUpdate?>(null)
    var nickNameUpdate: StateFlow<NickNameUpdate?> = _nickNameUpdate

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

    fun getAboutUpdate(header: Map<String, String>
                          ,aboutResponse: AboutResponse){
        viewModelScope.launch(dispatcher.io) {
            val result = aboutUpdateRepository.aboutUpdateRes(header,aboutResponse)

            when(result){
                is Resource.Success -> {
                    _aboutUpdate.emit(result.data)
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

    fun getNickNameUpdate(header: Map<String, String>
                       ,nickNameUpdate: NickNameUpdate){
        viewModelScope.launch(dispatcher.io) {
            val result = nickNameUpdateRepository.updateNameRepo(header,nickNameUpdate)

            when(result){
                is Resource.Success -> {
                    _nickNameUpdate.emit(result.data)
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