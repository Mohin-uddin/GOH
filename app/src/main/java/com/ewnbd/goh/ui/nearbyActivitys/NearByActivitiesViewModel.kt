package com.ewnbd.goh.ui.nearbyActivitys

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewnbd.goh.data.model.NearActivitiesCategoryListName
import com.ewnbd.goh.data.model.response_all_class.nearActivitys.NearActivitiesResponse
import com.ewnbd.goh.repository.categories.CategoryRepository
import com.ewnbd.goh.repository.categoryWiseSearch.CategoryWiseActivitySearchRepo
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
class NearByActivitiesViewModel @Inject constructor(
    private val categoryWiseActivitySearchRepo: CategoryWiseActivitySearchRepo,
    private var dispatcher: DispatcherProvider
): ViewModel() {
    private var _categoryWiseNearByActivitiesList = MutableStateFlow<NearActivitiesResponse?>(null)
    var categoryWiseNearByActivitiesList: StateFlow<NearActivitiesResponse?> = _categoryWiseNearByActivitiesList
    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()
    fun getNearActivitiesCategoryName(header: Map<String, String>
                                      ,categoryId: String){
        viewModelScope.launch(dispatcher.io) {
            val result = categoryWiseActivitySearchRepo.categorySearchRequestRepo(header,categoryId)

            when(result){
                is Resource.Success -> {
                    _categoryWiseNearByActivitiesList.emit(result.data)
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