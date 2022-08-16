package com.ewnbd.goh.ui.reportIssue

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.request_all_class.ReportIssueRequest
import com.ewnbd.goh.data.model.response_all_class.CategoryIssueListResponse
import com.ewnbd.goh.data.model.response_all_class.issue.IssueResponse
import com.ewnbd.goh.data.model.response_all_class.signup.SignUpResponse
import com.ewnbd.goh.data.model.response_all_class.trackingissue.TrackingIssueListResponse
import com.ewnbd.goh.repository.categoryIssueList.CategoryIssueListRepository
import com.ewnbd.goh.repository.reportIssue.ReportissueRepository
import com.ewnbd.goh.repository.signup.SignUpRepository
import com.ewnbd.goh.repository.trackingList.TrackingListRepository
import com.ewnbd.goh.utils.DispatcherProvider
import com.ewnbd.goh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportIssueViewmodel @Inject constructor(
    private val reportissueRepository: ReportissueRepository,
    private val trackingListRepository: TrackingListRepository,
    private val categoryIssueListRepository: CategoryIssueListRepository,
    private var dispatcher: DispatcherProvider
): ViewModel() {

    private var _reportIssueListReponse = MutableStateFlow<CategoryIssueListResponse?>(null)
    var reportIssueListReponse: StateFlow<CategoryIssueListResponse?> = _reportIssueListReponse.asStateFlow()

    private var _reportIssueReponse = MutableStateFlow<IssueResponse?>(null)
    var reportIssueReponse: StateFlow<IssueResponse?> = _reportIssueReponse.asStateFlow()

    private var _trackingIssue = MutableStateFlow<TrackingIssueListResponse?>(null)
    var trackingIssue: StateFlow<TrackingIssueListResponse?> = _trackingIssue.asStateFlow()

    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()
    fun getReportIssueRequest(header: Map<String, String>,reportIssueRequest: ReportIssueRequest){
        viewModelScope.launch(dispatcher.io) {
            val result = reportissueRepository.reportIssueRequest(header,reportIssueRequest)

            when(result){
                is Resource.Success -> {
                    _reportIssueReponse.emit(result.data)
                    //Log.e("dataCheck", "getLoginRequest: asd "+result.data?.user?.toString())
                }
                is Resource.Error -> {
                    Log.e("dataCheck", "getLoginRequest:aida ni "+result.message )
                    _responseCode.emit(result.message.toString())
                    Log.e("dataCheck", "getLoginRequest:aida pore "+result.message )
                }
            }
        }
    }

    fun getReportIssueList(header: Map<String, String>){
        viewModelScope.launch(dispatcher.io) {
            val result = categoryIssueListRepository.categoryIssueResponse(header)

            when(result){
                is Resource.Success -> {
                    _reportIssueListReponse.emit(result.data)
                    //Log.e("dataCheck", "getLoginRequest: asd "+result.data?.user?.toString())
                }
                is Resource.Error -> {
                    Log.e("dataCheck", "getLoginRequest:aida ni "+result.message )
                    _responseCode.emit(result.message.toString())
                    Log.e("dataCheck", "getLoginRequest:aida pore "+result.message )
                }
            }
        }
    }


    fun getTrackingRequest(header: Map<String, String>){
        viewModelScope.launch(dispatcher.io) {
            val result = trackingListRepository.trackingListRequest(header)

            when(result){
                is Resource.Success -> {
                    _trackingIssue.emit(result.data)
                    //Log.e("dataCheck", "getLoginRequest: asd "+result.data?.user?.toString())
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