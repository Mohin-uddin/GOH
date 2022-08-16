package com.ewnbd.goh.ui.editeProfile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.request_all_class.UpdateNameRequest
import com.ewnbd.goh.data.model.response_all_class.name_update.NameUpdateResponse
import com.ewnbd.goh.data.model.response_all_class.profilePicUpdate.ProfilePicUpdateResponse
import com.ewnbd.goh.repository.profilePicUpdate.ProfilePicUpdateRepository
import com.ewnbd.goh.repository.updateName.UpdatenameRepository
import com.ewnbd.goh.utils.DispatcherProvider
import com.ewnbd.goh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditProfileViewmodel @Inject constructor(
    private val profilePicUpdateRepository: ProfilePicUpdateRepository,
    private val updatenameRepository: UpdatenameRepository,
    private var dispatcher: DispatcherProvider
): ViewModel() {
    private var _profilePicUpdateResponse = MutableStateFlow<ProfilePicUpdateResponse?>(null)
    var profilePicUpdateResponse: StateFlow<ProfilePicUpdateResponse?> = _profilePicUpdateResponse

    private var _updateNameResponse = MutableStateFlow<NameUpdateResponse?>(null)
    var updateNameResponse: StateFlow<NameUpdateResponse?> = _updateNameResponse

    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()
    fun getProfileRequest(header: Map<String, String>
                          ,pic: MultipartBody.Part
    ){
        viewModelScope.launch(dispatcher.io) {
            val result = profilePicUpdateRepository.profilePicUpdateResponse(header,pic)

            when(result){
                is Resource.Success -> {
                    _profilePicUpdateResponse.emit(result.data)
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

    fun updateNameRequest(header: Map<String, String>,updateNameRequest: UpdateNameRequest){
        viewModelScope.launch(dispatcher.io) {
            val result = updatenameRepository.updateNameRepo(header,updateNameRequest)

            when(result){
                is Resource.Success -> {
                    _updateNameResponse.emit(result.data)
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