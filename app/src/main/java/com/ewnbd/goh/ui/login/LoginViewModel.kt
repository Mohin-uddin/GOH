package com.ewnbd.goh.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.response_all_class.login.LoginResponse
import com.ewnbd.goh.repository.login.LoginRepository
import com.ewnbd.goh.utils.DispatcherProvider
import com.ewnbd.goh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel  @Inject constructor(
    private val loginRepository: LoginRepository,
    private var dispatcher: DispatcherProvider
): ViewModel() {
    private var _loginResponse = MutableStateFlow<LoginResponse?>(null)
    var loginResponse: StateFlow<LoginResponse?> = _loginResponse



    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()
    fun getLoginRequest(email: String, password: String){
        viewModelScope.launch(dispatcher.io) {
            val result = loginRepository.loginResponse(email,password)

            when(result){
                is Resource.Success -> {
                    _loginResponse.emit(result.data)
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
