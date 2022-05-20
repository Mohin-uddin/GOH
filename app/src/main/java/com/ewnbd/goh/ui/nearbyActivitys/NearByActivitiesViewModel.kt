package com.ewnbd.goh.ui.nearbyActivitys

import androidx.lifecycle.ViewModel
import com.ewnbd.goh.data.model.NearActivitiesCategoryListName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NearByActivitiesViewModel : ViewModel() {
    private var _nearByActivitiesList = MutableStateFlow<List<NearActivitiesCategoryListName>>(listOf())
    val nearByActivitiesList : StateFlow<List<NearActivitiesCategoryListName>> = _nearByActivitiesList

    fun getNearActivitiesCategoryName(){
        var nearByActivitiesCategoryNameList = ArrayList<NearActivitiesCategoryListName>()
        nearByActivitiesCategoryNameList.add(NearActivitiesCategoryListName("All"))
        nearByActivitiesCategoryNameList.add(NearActivitiesCategoryListName("Football"))
        nearByActivitiesCategoryNameList.add(NearActivitiesCategoryListName("Cricket"))
        nearByActivitiesCategoryNameList.add(NearActivitiesCategoryListName("Tennis"))
        nearByActivitiesCategoryNameList.add(NearActivitiesCategoryListName("Squash"))
        nearByActivitiesCategoryNameList.add(NearActivitiesCategoryListName("Baseball"))
        _nearByActivitiesList.value=nearByActivitiesCategoryNameList
    }
}