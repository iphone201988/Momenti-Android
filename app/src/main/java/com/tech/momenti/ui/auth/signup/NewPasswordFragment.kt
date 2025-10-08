package com.tech.momenti.ui.auth.signup

import android.text.InputType
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.utils.BindingUtils
import com.tech.momenti.base.utils.Status
import com.tech.momenti.base.utils.showToast
import com.tech.momenti.data.api.Constants
import com.tech.momenti.data.api.SimpleApiResponse
import com.tech.momenti.databinding.FragmentNewPasswordBinding
import com.tech.momenti.ui.auth.AuthCommonVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPasswordFragment : BaseFragment<FragmentNewPasswordBinding>() {

    private val viewModel : AuthCommonVM by viewModels()

    private var email : String ? = null


    override fun onCreateView(view: View) {
        initOnClick()
        initView()
        initObserver()
    }

    private fun initObserver() {
        viewModel.observeCommon.observe(viewLifecycleOwner, Observer{
            when(it?.status){
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS ->  {
                    hideLoading()
                    val myDataModel : SimpleApiResponse? = BindingUtils.parseJson(it.data.toString())
                    if (myDataModel != null){
                        if (myDataModel.message != null){
                            findNavController().navigate(R.id.fragmentPasswordChanged)
                        }
                    }
                }
                Status.ERROR ->  {
                    hideLoading()
                    showToast(it.message.toString())
                }
                else ->  {

                }
            }
        })
    }

    private fun initView() {
        email =  arguments?.getString("email")
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_new_password
    }

    override fun getViewModel(): BaseViewModel {
         return viewModel
    }


    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when(it?.id){
                R.id.continueBtn ->{
                    if (isEmptyField()){
                        val data = HashMap<String, Any>()
                        data["email"] = email.toString()
                        data["newPassword"] = binding.etPassword.text.toString().trim()
                        viewModel.resetPassword(data, Constants.RESET_PASSWORD)
                 //       findNavController().navigate(R.id.fragmentPasswordChanged)

                    }

                }
                R.id.ivBack ->{
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
                R.id.showPassword ->{
                    signUpShowHidePassword()
                }
                R.id.showConfirmPassword ->{
                    signUpShowHideConfirmPassword()
                }
            }
        })
    }


    private fun isEmptyField() : Boolean {
        var password = binding.etPassword.text.toString().trim()
        if (TextUtils.isEmpty(binding.etPassword.text.toString().trim())){
            showToast("Please enter password")
            return false
        }
        if (TextUtils.isEmpty(binding.etConfirmPassword.text.toString().trim())){
            showToast("Please enter confirm password")
            return false
        }
        if (password != binding.etConfirmPassword.text.toString().trim()){
            showToast("Password and confirm password not matched")
            return false
        }
        return true
    }


    private fun signUpShowHidePassword() {
        if (binding.etPassword.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            binding.showPassword.setImageResource(R.drawable.iv_show_eye)
            binding.etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            binding.showPassword.setImageResource(R.drawable.iv_hide_eye)
            binding.etPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        binding.etPassword.setSelection(binding.etPassword.length())
    }

    private fun signUpShowHideConfirmPassword() {
        if (binding.etConfirmPassword.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            binding.showConfirmPassword.setImageResource(R.drawable.iv_show_eye)
            binding.etConfirmPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            binding.showConfirmPassword.setImageResource(R.drawable.iv_hide_eye)
            binding.etConfirmPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        binding.etConfirmPassword.setSelection(binding.etConfirmPassword.length())
    }


}