package com.ewnbd.goh.ui.changePassword

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.request_all_class.ChangePasswordRequest
import com.ewnbd.goh.data.model.response_all_class.ForgetPasswordResponse
import com.ewnbd.goh.data.model.response_all_class.organizationRequestList.OrganizationJoinRequestListResponse
import com.ewnbd.goh.repository.changePassword.ChangepasswordRepository
import com.ewnbd.goh.repository.forgetpassword.ForgetPasswordRepository
import com.ewnbd.goh.repository.organizationParticipetor.OrganizationParticipetorRepo
import com.ewnbd.goh.utils.DispatcherProvider
import com.ewnbd.goh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private var cahngePasswordRepository: ChangepasswordRepository,
    private var dispatcher: DispatcherProvider
): ViewModel() {
    private var _passwordChange = MutableStateFlow<ForgetPasswordResponse?>(null)
    var passwordChange: StateFlow<ForgetPasswordResponse?> = _passwordChange

    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()

    fun getChangePasswordRequest(header: Map<String, String>,changePasswordRequest: ChangePasswordRequest
                          ,userId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = cahngePasswordRepository.changePasswordRepository(header,changePasswordRequest,userId)

            when(result){
                is Resource.Success -> {
                    _passwordChange.emit(result.data)
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
    fun dataClear (){
        _responseCode.value = ""
        _passwordChange.value = null
    }
}