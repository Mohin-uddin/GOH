package com.ewnbd.goh.ui.fragment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.response_all_class.activity_categorylist.ActivityCategoryListResponse
import com.ewnbd.goh.data.model.response_all_class.nearActivitys.NearActivitiesResponse
import com.ewnbd.goh.data.model.response_all_class.organigationActivities.OrganizedActivitiesResponse
import com.ewnbd.goh.repository.organization.OrganizationRepository
import com.ewnbd.goh.repository.participated.ParticipatedActivityRepo
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
class ActivityOrganizationAndPartiViewmodel@Inject constructor(
    private val organizationRepository: OrganizationRepository,
    private val participatedActivityRepo: ParticipatedActivityRepo,
    private var dispatcher: DispatcherProvider
) : ViewModel() {
    private var _organizationActivityResponse = MutableStateFlow<OrganizedActivitiesResponse?>(null)
    var organizationActivityResponse: StateFlow<OrganizedActivitiesResponse?> = _organizationActivityResponse

    private var _participatedActivityResponse = MutableStateFlow<OrganizedActivitiesResponse?>(null)
    var participatedActivityResponse: StateFlow<OrganizedActivitiesResponse?> = _participatedActivityResponse



    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()
    fun getOrganizationRequest(header: Map<String, String>
                         ,userId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = organizationRepository.organizationRequestRepo(header,userId)

            when(result){
                is Resource.Success -> {
                    _organizationActivityResponse.emit(result.data)
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

    fun getParticipatedByRequest(header: Map<String, String>
                                 ,userId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = participatedActivityRepo.participatedRequestRepo(header,userId)

            when(result){
                is Resource.Success -> {
                    _participatedActivityResponse.emit(result.data)
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