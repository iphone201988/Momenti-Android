package com.tech.momenti.ui.auth.signup

import android.os.Bundle
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
import com.tech.momenti.data.model.SignupApiResponse
import com.tech.momenti.databinding.FragmentCreateAccountBinding
import com.tech.momenti.ui.auth.AuthCommonVM
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateAccountFragment : BaseFragment<FragmentCreateAccountBinding>() {

    private val viewModel : AuthCommonVM by viewModels()

    private var email : String ? = null

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
                    try {
                        val myDataModel : SignupApiResponse ? = BindingUtils.parseJson(it.data.toString())
                        if (myDataModel != null){
                            if (myDataModel.data != null){
                                val bundle = Bundle()
                                bundle.putString("side","create")
                                bundle.putString("email", email)
                                findNavController().navigate(R.id.fragmentVerifyOtp,bundle)
                            }
                        }
                    }catch (e : Exception){
                        showToast(e.message.toString())
                    }

                }
                Status.ERROR -> {
                    hideLoading()
                }
                else->{

                }
            }
        })
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_create_account
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }


    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when(it?.id){
                R.id.signUpBtn ->{
                    if (isEmptyField()){
                        email = binding.etEmail.text.toString().trim()
                        val data = HashMap<String, Any>()
                        data["email"] = binding.etEmail.text.toString().trim()
                        data["password"]  = binding.etPassword.text.toString().trim()
                        data["name"] = binding.etName.text.toString().trim()
                        viewModel.createAccount(data, Constants.SIGN_UP)

                    }


                }
                R.id.showPassword ->{
                    signUpShowHidePassword()
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


    private fun isEmptyField() : Boolean {
        if (TextUtils.isEmpty(binding.etName.text.toString().trim())){
            showToast("Please enter name")
            return false
        }
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