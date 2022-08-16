package com.ewnbd.goh.ui.forgotpassword

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.request_all_class.CodeMatchingRequest
import com.ewnbd.goh.data.model.response_all_class.activity_categorylist.ActivityCategoryListResponse
import com.ewnbd.goh.data.model.response_all_class.codeMatch.CodeMatchResponse
import com.ewnbd.goh.data.model.response_all_class.receatCode.RecetGenerateCodeResponse
import com.ewnbd.goh.repository.categories.CategoryRepository
import com.ewnbd.goh.repository.codeMtach.CodeMatchRepository
import com.ewnbd.goh.repository.forgetpassword.recetCodeGenerate.RecetCodeGenerateRepository
import com.ewnbd.goh.repository.nearActivity.NearActivitysListRepository
import com.ewnbd.goh.utils.DispatcherProvider
import com.ewnbd.goh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    private var codeMatchRepository: CodeMatchRepository,
    private var recetCodeGenerateRepository: RecetCodeGenerateRepository,
    private var dispatcher: DispatcherProvider
): ViewModel() {

    private var _recetCodeResponse = MutableStateFlow<RecetGenerateCodeResponse?>(null)
    var recetCodeResponse: StateFlow<RecetGenerateCodeResponse?> = _recetCodeResponse
    private var _matchCodeResponse = MutableStateFlow<CodeMatchResponse?>(null)
    var matchCodeResponse: StateFlow<CodeMatchResponse?> = _matchCodeResponse


    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()
    fun getRecentCodeGenerateRequest(userName: String){
        viewModelScope.launch(dispatcher.io) {
            val result = recetCodeGenerateRepository.recetCodeResponse(userName)

            when(result){
                is Resource.Success -> {
                    _recetCodeResponse.emit(result.data)
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

    fun getMatchCodeRequest(codeMatchingRequest: CodeMatchingRequest,mobileNumber: String){
        viewModelScope.launch(dispatcher.io) {
            val result = codeMatchRepository.codeMatchResponse(codeMatchingRequest,mobileNumber)

            when(result){
                is Resource.Success -> {
                    _matchCodeResponse.emit(result.data)
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