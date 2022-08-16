package com.ewnbd.goh.ui.fragment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.response_all_class.ShareActivityResponse
import com.ewnbd.goh.data.model.response_all_class.chat.ChatListResponse
import com.ewnbd.goh.data.model.response_all_class.chatDetails.ChatDetailsResponse
import com.ewnbd.goh.repository.activityShare.ChatBlockRepository
import com.ewnbd.goh.repository.caht.ChatRepository
import com.ewnbd.goh.repository.chatDetails.ChatDetailsRespository
import com.ewnbd.goh.utils.DispatcherProvider
import com.ewnbd.goh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val chatDetailsRespository: ChatDetailsRespository,
    private val chatBlockRepository: ChatBlockRepository,
    private var dispatcher: DispatcherProvider,
): ViewModel() {
    private var _chatListResponse = MutableStateFlow<ChatListResponse?>(null)
    var chatListResponse: StateFlow<ChatListResponse?> = _chatListResponse

    private var _chatDetailsResponse = MutableStateFlow<ChatDetailsResponse?>(null)
    var chatDetailsResponse: StateFlow<ChatDetailsResponse?> = _chatDetailsResponse.asStateFlow()

    private var _chatBlock = MutableStateFlow<ShareActivityResponse?>(null)
    var chatBlock: StateFlow<ShareActivityResponse?> = _chatBlock.asStateFlow()

    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()

    fun getProfileRequest(header: Map<String, String>
                          ){
        viewModelScope.launch(dispatcher.io) {
            val result = chatRepository.chatRepository(header)

            when(result){
                is Resource.Success -> {
                    _chatListResponse.emit(result.data)
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

    fun getChatDetails(header: Map<String, String>,chatId: String
    ){
        viewModelScope.launch(dispatcher.io) {
            val result = chatDetailsRespository.chatRepository(header,chatId)

            when(result){
                is Resource.Success -> {
                    _chatDetailsResponse.emit(result.data)
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

    fun getChatBlock(header: Map<String, String>,chatId: String
    ){
        viewModelScope.launch(dispatcher.io) {
            val result = chatBlockRepository.chatBlockResponse(header,chatId)

            when(result){
                is Resource.Success -> {
                    _chatBlock.emit(result.data)
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