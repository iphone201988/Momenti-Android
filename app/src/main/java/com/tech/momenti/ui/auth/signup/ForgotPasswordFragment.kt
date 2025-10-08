package com.tech.momenti.ui.auth.signup

import android.os.Bundle
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
import com.tech.momenti.data.model.ForgotPasswordApiResponse
import com.tech.momenti.data.model.SignupApiResponse
import com.tech.momenti.databinding.FragmentForgotPasswordBinding
import com.tech.momenti.ui.auth.AuthCommonVM
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>() {

    private val viewModel : AuthCommonVM by viewModels()

    private var email : String ? = null


    override fun onCreateView(view: View) {
        initOnClick()
        initObserver()
    }

    private fun initObserver() {
        viewModel.observeCommon.observe(viewLifecycleOwner, Observer{
            when(it?.status){
                Status.LOADING ->  {
                    showLoading()
                }
                Status.SUCCESS ->  {
                    hideLoading()
                    try {
                        val myDataModel : ForgotPasswordApiResponse ? = BindingUtils.parseJson(it.data.toString())
                        if (myDataModel != null){
                            if (myDataModel.user != null){
                                val bundle = Bundle()
                                bundle.putString("email", email)
                                findNavController().navigate(R.id.fragmentVerifyOtp,bundle)
                            }
                        }
                    }catch (e  : Exception){
                        showToast(e.message.toString())
                    }

                }
                Status.ERROR -> {
                    hideLoading()
                    showToast(it.message.toString())
                }
                else ->{

                }
            }
        })
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_forgot_password

    }

    override fun getViewModel(): BaseViewModel {
      return viewModel
    }


    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when(it?.id){
                R.id.saveBtn ->{
                    if (isEmptyField()){
                        email = binding.etEmail.text.toString().trim()
                        val data = HashMap<String, Any>()
                        data["email"] = binding.etEmail.text.toString().trim()
                        viewModel.forgotPassword(data, Constants.FORGOT_PASSWORD)



                    }

                }
                R.id.ivBack ->{
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }


    private fun isEmptyField() : Boolean {
        if (TextUtils.isEmpty(binding.etEmail.text.toString().trim())) {
            showToast("Please enter email")
            return false
        }
           return true
    }

}