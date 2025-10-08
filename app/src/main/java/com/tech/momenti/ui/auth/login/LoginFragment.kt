package com.tech.momenti.ui.auth.login

import android.content.Intent
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
import com.tech.momenti.data.model.VerifyOtpApiResponse
import com.tech.momenti.databinding.FragmentLoginBinding
import com.tech.momenti.ui.auth.AuthCommonVM
import com.tech.momenti.ui.home_screen.HomeDashboardActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel : AuthCommonVM by viewModels()
    override fun onCreateView(view: View) {
        initOnClick()
        initObserver()
    }

    private fun initObserver() {
        viewModel.observeCommon.observe(viewLifecycleOwner, Observer{
            when(it?.status){
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    hideLoading()
                    val myDataModel : VerifyOtpApiResponse ? = BindingUtils.parseJson(it.data.toString())
                    if (myDataModel != null){
                        if (myDataModel.user != null){
                            sharedPrefManager.setLoginData(myDataModel)
                            val intent = Intent(requireContext(), HomeDashboardActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
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

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when(it?.id){
                R.id.signUp ->{
                    findNavController().navigate(R.id.fragmentCreateAccount)

                }
                R.id.forgotPassword ->{
                    findNavController().navigate(R.id.fragmentForgotPassword)

                }
                R.id.showPassword ->{
                    signUpShowHidePassword()
                }
                R.id.loginBtn ->{
                    if (isEmptyField()){
                        val data = HashMap<String, Any>()
                        data["email"] = binding.etEmail.text.toString().trim()
                        data["password"] = binding.etPassword.text.toString().trim()
                        viewModel.login(data, Constants.LOGIN)

                    }

                }
            }
        })
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

    override fun getLayoutResource(): Int {
        return R.layout.fragment_login
    }

    override fun getViewModel(): BaseViewModel {
       return viewModel
    }


    private fun isEmptyField() : Boolean {
        if (TextUtils.isEmpty(binding.etEmail.text.toString().trim())){
            showToast("Please enter email")
             return false
        }
        if (TextUtils.isEmpty(binding.etPassword.text.toString().trim())){
            showToast("Please enter password")
            return false
        }
        return  true
    }
}