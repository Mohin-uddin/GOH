package com.ewnbd.goh.ui.resetPassword

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.request_all_class.forget.ForgetpasswordRequest
import com.ewnbd.goh.data.model.response_all_class.ForgetPasswordResponse
import com.ewnbd.goh.data.model.response_all_class.signup.SignUpResponse
import com.ewnbd.goh.repository.forgetpassword.ForgetPasswordRepository
import com.ewnbd.goh.repository.forgetpassword.recetCodeGenerate.RecetCodeGenerateRepository
import com.ewnbd.goh.utils.DispatcherProvider
import com.ewnbd.goh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentPasswordViewModel  @Inject constructor(
    private var forgetPasswordRepository: ForgetPasswordRepository,
    private var dispatcher: DispatcherProvider
): ViewModel() {
    private var _passwordChangeResponse = MutableStateFlow<ForgetPasswordResponse?>(null)
    var passwordChangeResponse: StateFlow<ForgetPasswordResponse?> = _passwordChangeResponse.asStateFlow()



    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()
    fun getForgetRequest(forgetpasswordRequest: ForgetpasswordRequest,
                         mobileNumber: String, code: String){
        viewModelScope.launch(dispatcher.io) {
            val result = forgetPasswordRepository.forgetPasswordResponse(forgetpasswordRequest,mobileNumber,code)

            when(result){
                is Resource.Success -> {
                    _passwordChangeResponse.emit(result.data)
                }
                is Resource.Error -> {
                    Log.e("dataCheck", "getLoginRequest:aida ni "+result.message )
                    _responseCode.emit(result.message.toString())
                    Log.e("dataCheck", "getLoginRequest:aida pore "+result.message )
                }
            }
        }
    }
    fun getDataClear(){
        _responseCode.value="0"
    }
}