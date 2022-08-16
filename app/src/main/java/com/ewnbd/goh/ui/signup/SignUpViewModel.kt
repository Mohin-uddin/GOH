package com.ewnbd.goh.ui.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.response_all_class.signup.SignUpResponse
import com.ewnbd.goh.repository.signup.SignUpRepository
import com.ewnbd.goh.utils.DispatcherProvider
import com.ewnbd.goh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository,
    private var dispatcher: DispatcherProvider
) : ViewModel() {

    private var _signUpResponse = MutableStateFlow<SignUpResponse?>(null)
    var signUpResponse: StateFlow<SignUpResponse?> = _signUpResponse.asStateFlow()



    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()
    fun getSignUpRequest(userName:String,mobile:String,email: String, password: String,confirmPassword:String){
        viewModelScope.launch(dispatcher.io) {
            val result = signUpRepository.signUpResponse(userName,mobile,email,password,confirmPassword)

            when(result){
                is Resource.Success -> {
                    _signUpResponse.emit(result.data)
                    Log.e("dataCheck", "getLoginRequest: asd "+result.data?.user?.toString())
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