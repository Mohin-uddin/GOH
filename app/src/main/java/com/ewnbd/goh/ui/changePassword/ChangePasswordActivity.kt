package com.ewnbd.goh.ui.changePassword

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.request_all_class.ChangePasswordRequest
import com.ewnbd.goh.databinding.ActivityChangePasswordBinding
import com.ewnbd.goh.databinding.ActivityFriendProfileBinding
import com.ewnbd.goh.ui.signup.SignUpViewModel
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.gone
import com.ewnbd.goh.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.flow.collectLatest
import java.util.HashMap

@AndroidEntryPoint
class ChangePasswordActivity : AppCompatActivity() {
    lateinit var binding : ActivityChangePasswordBinding
    private lateinit var dialouge: Dialog
    private  val viewModel: ChangePasswordViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ivBack.setOnClickListener {
            onBackPressed()
        }
        passwordHideAndGone()
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        val tokenGenerate = HashMap<String,String>()
        val userId = preference?.getString("userId","")?:""
        tokenGenerate["Authorization"] = tokenData
        tvConfirmPass.setOnClickListener {
            if (validation()){
                dialouge= ConstValue.animation(this,0)
                val changePasswordRequest = ChangePasswordRequest(binding.etOldPassword.text.toString()
                    ,binding.etNewPassword.text.toString(),binding.etNeConfirmPassword.text.toString())
                viewModel.getChangePasswordRequest(tokenGenerate,changePasswordRequest,userId)
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.passwordChange.collectLatest {
                it?.let {
                    if (::dialouge.isInitialized){
                        dialouge.dismiss()
                    }
                    Toast.makeText(this@ChangePasswordActivity,"Successfully password update",Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.responseCode.collectLatest {
                it.let {
                    if (::dialouge.isInitialized){
                        dialouge.dismiss()
                    }
                    if (it!="0"&&it=="400"){
                        Toast.makeText(this@ChangePasswordActivity,"Old password is not match",Toast.LENGTH_SHORT).show()
                    }
                    viewModel.dataClear()
                }
            }
        }
    }

    private fun validation(): Boolean {
        if (binding.etOldPassword.text.toString().isEmpty()){
            binding.etOldPassword.requestFocus()
            binding.etOldPassword.error=getString(R.string.your_password)
            return false
        }
        if (binding.etNewPassword.text.toString().isEmpty()){
            binding.etNewPassword.requestFocus()
            binding.etNewPassword.error=getString(R.string.your_password)
            return false
        }
        if (binding.etNeConfirmPassword.text.toString().isEmpty()){
            binding.etNeConfirmPassword.requestFocus()
            binding.etNeConfirmPassword.error=getString(R.string.your_password)
            return false
        }
        if (binding.etNewPassword.text.toString().length<4){
            binding.etNewPassword.requestFocus()
            binding.etNewPassword.error=getString(R.string.password_sort)
            return false
        }
        if (binding.etNeConfirmPassword.text.toString().length<4){
            binding.etNeConfirmPassword.requestFocus()
            binding.etNeConfirmPassword.error=getString(R.string.password_sort)
            return false
        }
        if (binding.etNewPassword.text.toString()!=binding.etNeConfirmPassword.text.toString()){
            binding.etNeConfirmPassword.requestFocus()
            binding.etNeConfirmPassword.error=getString(R.string.password_match)
            return false
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
    private fun passwordHideAndGone() {
        binding.ivSVHide.setOnClickListener {
            binding.ivSPView.show()
            binding.ivSVHide.gone()
            binding.etOldPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }
        binding.ivSPView.setOnClickListener {
            binding.ivSVHide.show()
            binding.ivSPView.gone()
            binding.etOldPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        }

        binding.ivConVHide.setOnClickListener {
            binding.ivConPView.show()
            binding.ivConVHide.gone()
            binding.etNewPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }
        binding.ivConPView.setOnClickListener {
            binding.ivConVHide.show()
            binding.ivConPView.gone()
            binding.etNewPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        }

        binding.ivNConVHide.setOnClickListener {
            binding.ivNConPView.show()
            binding.ivNConVHide.gone()
            binding.etNeConfirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }
        binding.ivNConPView.setOnClickListener {
            binding.ivNConVHide.show()
            binding.ivNConPView.gone()
            binding.etNeConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

}