package com.tech.momenti.ui.auth.signup

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.databinding.FragmentCreateAccountBinding
import com.tech.momenti.ui.auth.AuthCommonVM
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateAccountFragment : BaseFragment<FragmentCreateAccountBinding>() {

    private val viewModel : AuthCommonVM by viewModels()

    override fun onCreateView(view: View) {
        initOnClick()
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
                    val bundle = Bundle()
                    bundle.putString("side","create")
                    findNavController().navigate(R.id.fragmentVerifyOtp,bundle)

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
}