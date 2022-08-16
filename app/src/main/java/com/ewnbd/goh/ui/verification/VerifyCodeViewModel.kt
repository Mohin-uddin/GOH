package com.ewnbd.goh.ui.verification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.response_all_class.verification.VeificationResponse
import com.ewnbd.goh.repository.verification.VerificationRepository
import com.ewnbd.goh.utils.DispatcherProvider
import com.ewnbd.goh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyCodeViewModel @Inject constructor(
    private val verificationRepository: VerificationRepository,
    private var dispatcher: DispatcherProvider
): ViewModel() {
    private var _verifactionResponse = MutableStateFlow<VeificationResponse?>(null)
    var verifactionResponse: StateFlow<VeificationResponse?> = _verifactionResponse.asStateFlow()



    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()
    fun getVerificationRequest(userId: String, code: String){
        viewModelScope.launch(dispatcher.io) {
            val result = verificationRepository.verificationResponse(userId,code)

            when(result){
                is Resource.Success -> {
                    _verifactionResponse.emit(result.data)
                    Log.e("dataCheck", "getLoginRequest: asd "+result.message )
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