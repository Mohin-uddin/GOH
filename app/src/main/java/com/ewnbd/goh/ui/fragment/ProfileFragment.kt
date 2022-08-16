package com.ewnbd.goh.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.ewnbd.goh.MainActivity
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.ProfileActivity
import com.ewnbd.goh.ui.changePassword.ChangePasswordActivity
import com.ewnbd.goh.ui.fragment.viewmodel.NearActivityListViewModel
import com.ewnbd.goh.ui.fragment.viewmodel.ProfileViewModel
import com.ewnbd.goh.ui.friends.FriendsActivity
import com.ewnbd.goh.ui.login.LoginActivity
import com.ewnbd.goh.ui.privecy_policy.PrivecyPolicyActivity
import com.ewnbd.goh.ui.reportIssue.ReportIssueActivity
import com.ewnbd.goh.ui.termsCondition.TearmsAndCondition
import com.ewnbd.goh.utils.ConstValue.BASE_URL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.HashMap

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    lateinit var tokenGenerate : HashMap<String,String>
    private  val viewModel: ProfileViewModel by viewModels()
    var userId=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preference = context?.getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
         tokenGenerate = HashMap<String,String>()
         userId = preference?.getString("userId","")?:""
        tokenGenerate["Authorization"] = tokenData

        llFriends.setOnClickListener {
            startActivity(Intent(context,FriendsActivity::class.java))
        }
        llProfile.setOnClickListener {
            startActivity(Intent(context,ProfileActivity::class.java))
        }
        ivSignOut.setOnClickListener {
            val editor = preference?.edit()
            editor?.putBoolean("isLoggedIn", false)
            editor?.apply()
            startActivity(Intent(context, LoginActivity::class.java))

        }
        llPrivacyPolicy.setOnClickListener {
            startActivity(Intent(context, PrivecyPolicyActivity::class.java))
        }
        llTearmsAndCondition.setOnClickListener {
            startActivity(Intent(context, TearmsAndCondition::class.java))
        }
        llReportOnIssue.setOnClickListener {
            startActivity(Intent(context, ReportIssueActivity::class.java))
        }
        llPasswordChange.setOnClickListener {
            startActivity(Intent(context, ChangePasswordActivity::class.java))
        }



        lifecycleScope.launchWhenCreated {
            viewModel.profileResponse.collectLatest {
                if(it!=null) {
                    try {
                        if (it.data.user.dp!=null) {

                            civProfilePic.load("http://167.99.32.192" + it.data.user.dp) {
                                transformations(CircleCropTransformation())
                            }
                        }
                        tvPersonName.text=it.data.user.full_name
                        tvAccountEmail.text=it.data.nickname
                    }catch (error: Exception){
                        Toast.makeText(context,error.toString(),Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }

        lifecycleScope.launch{
            viewModel.responseCode.collectLatest {
                if (it!="0"&&it!="200"){
                    Toast.makeText(context,"Server error $it",Toast.LENGTH_SHORT).show()
                    viewModel.getDataClear()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProfileRequest(tokenGenerate,userId)
    }

}