package com.tech.momenti.ui.auth.signup

import android.text.TextUtils
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.utils.showToast
import com.tech.momenti.databinding.FragmentForgotPasswordBinding
import com.tech.momenti.ui.auth.AuthCommonVM
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>() {

    private val viewModel : AuthCommonVM by viewModels()


    override fun onCreateView(view: View) {
     initOnClick()
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
                        findNavController().navigate(R.id.fragmentVerifyOtp)

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