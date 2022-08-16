package com.ewnbd.goh.utils

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.Window
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.NearActivitiesCategoryListName
import com.ewnbd.goh.data.model.response_all_class.SelectChatDetailsData
import com.ewnbd.goh.data.model.response_all_class.nearActivitys.NearActivitiesResponse
import com.ewnbd.goh.data.model.response_all_class.nearActivitys.Result
import com.ewnbd.goh.data.model.response_all_class.receatCode.RecetGenerateCodeResponse
import kotlinx.android.synthetic.main.item_dialuge_beef.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.*

object ConstValue {

    var issueStateCategoryId: String=""
    var requestType: Int=0
    var lastSms: SelectChatDetailsData?=null
    var blockStatus: Boolean = false
    var editeState: Int = 0
    var organizationState: Int=0
    var chatId: String = ""
    var activityId: String =""
    var ageState =-1
    var state =-1
    var mobileNumber: String=""
    var longitudeValue: String=""
    var latitudeValue: String=""
    var selectAddress: String=""
    var age_range: Int=0
    var activity_timeId: Int=0
    var userId: String=""
    lateinit var filterResult: NearActivitiesResponse
    var activity_date_slot: String=""
    var activity_time: String=""
    var activity_privecy: String=""
    var activty_gender: String=""
    var attendent: String = ""
    var about: String=""
    var categoryId: String?=""
    var activity_name: String=""
    var activity_place: String=""
    lateinit var activity_img: Uri
    lateinit var matchCode: RecetGenerateCodeResponse
    lateinit var activityItem: Result
    var categoryWisedata= NearActivitiesCategoryListName("",0)
    const val BASE_URL = "http://167.99.32.192/api/"
    const val DATE_PICKER_TAG = "MaterialDatePicker"

     var dp: MultipartBody.Part? = null
     var name: RequestBody? =null
     var desc: RequestBody? =null
     var min_participate: RequestBody? =null
     var gender: RequestBody? =null
     var privacy: RequestBody? =null
     var latitude: RequestBody? =null
    var longitude: RequestBody? =null
    var place: RequestBody? =null
    var activity_date: RequestBody? =null
    var category_id: RequestBody? =null
    var age_range_id: RequestBody? =null
    var startTime: RequestBody? =null
    var endTime: RequestBody? =null
    var signUpCode = "0"

      fun animation(context: Context, state:Int): Dialog {
        val dialog = Dialog(context, R.style.CommonDialog2)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.item_dialuge_beef)
        val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
            dialog.ivLoader,
            PropertyValuesHolder.ofFloat("scaleX", 1.2f),
            PropertyValuesHolder.ofFloat("scaleY", 1.2f))
        scaleDown.duration = 310

        scaleDown.repeatCount = ObjectAnimator.INFINITE
        scaleDown.repeatMode = ObjectAnimator.REVERSE

        if (state==0) {
            dialog.show()
            scaleDown.start()
        }
        else
        {
            Log.e("asdasdasd", "animation: " )
            dialog.dismiss()
            scaleDown.cancel()
        }

        return dialog
    }

     fun getAge(year: Int, month: Int, day: Int): String {
        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()
        dob[year, month] = day
        var age =
            today[Calendar.YEAR] - dob[Calendar.YEAR]
        if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
            age--
        }
        val ageInt = age
        return ageInt.toString()
    }

    const val WEB_SOCKET_URL = "ws://167.99.32.192:8000/notifications/consumer/"
}