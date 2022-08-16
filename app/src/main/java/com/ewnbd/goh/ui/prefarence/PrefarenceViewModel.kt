package com.ewnbd.goh.ui.prefarence

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.request_all_class.InterserAddRequest
import com.ewnbd.goh.data.model.request_all_class.basic_profile.BasicProfileRequest
import com.ewnbd.goh.data.model.response_all_class.InterestItem
import com.ewnbd.goh.data.model.response_all_class.basicProfile.BasicProfileResponse
import com.ewnbd.goh.data.model.response_all_class.interestList.InterestListResponse
import com.ewnbd.goh.data.model.response_all_class.interstAddResposne.InterestAddResponse
import com.ewnbd.goh.data.model.response_all_class.mapSearch.MapSearchResponse
import com.ewnbd.goh.repository.MapRepository
import com.ewnbd.goh.repository.addBasicProfile.AddBasicProfileRepository
import com.ewnbd.goh.repository.interestAdd.InterestAddRepository
import com.ewnbd.goh.repository.interestList.InterestListRepository
import com.ewnbd.goh.utils.DispatcherProvider
import com.ewnbd.goh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrefarenceViewModel @Inject constructor(
    private var addBasicProfileRepository: AddBasicProfileRepository,
    private var dispatcher: DispatcherProvider,
    private var interestAddRepository: InterestAddRepository,
    private var interestListRepository: InterestListRepository,
    private var mapRepository: MapRepository
): ViewModel() {
    private var _interestAddResponse = MutableStateFlow<InterestAddResponse?>(null)
    var interestAddResponse: StateFlow<InterestAddResponse?> = _interestAddResponse

    private var _interestResponse = MutableStateFlow<InterestListResponse?>(null)
    var interestResponse: StateFlow<InterestListResponse?> = _interestResponse

    private var _prefarenceResponse = MutableStateFlow<BasicProfileResponse?>(null)
    var prefarenceResponse: StateFlow<BasicProfileResponse?> = _prefarenceResponse

    private var _location = MutableStateFlow<LocationData?>(null)
    var location: StateFlow<LocationData?> = _location


    private var _locationMapResult = MutableStateFlow<MapSearchResponse?>(null)
    var locationMapResult: StateFlow<MapSearchResponse?> = _locationMapResult

    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()
    fun getPrefarenceRequest(header: String,basicProfileRequest: BasicProfileRequest){
        viewModelScope.launch(dispatcher.io) {
            val result = addBasicProfileRepository.addBasicProfileResponse(header,basicProfileRequest)

            when(result){
                is Resource.Success -> {
                    _prefarenceResponse.emit(result.data)
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

    fun getAddInterest(header: Map<String, String>
                                      ,userId: String,interserAddRequest: InterserAddRequest){
        viewModelScope.launch(dispatcher.io) {
            val result = interestAddRepository.interestAddResponse(header,userId,interserAddRequest)

            when(result){
                is Resource.Success -> {
                    _interestAddResponse.emit(result.data)
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

    fun getInterestList(header: Map<String, String>){
        viewModelScope.launch(dispatcher.io) {
            val result = interestListRepository.interestList(header)

            when(result){
                is Resource.Success -> {
                    _interestResponse.emit(result.data)
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
    fun getMapResponse(mapApi: String,searchData: String){
        viewModelScope.launch(dispatcher.io) {
            val result = mapRepository.mapResponse(mapApi,searchData)

            when(result){
                is Resource.Success -> {
                    _locationMapResult.emit(result.data)
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
    fun latitudeData(city: String,latitude:String,longitude: String){
        viewModelScope.launch {
            val locationData = LocationData(city,latitude,longitude)
            _location.emit(locationData)
        }

    }

}