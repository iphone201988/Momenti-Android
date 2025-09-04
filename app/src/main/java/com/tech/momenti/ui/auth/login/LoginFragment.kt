package com.tech.momenti.ui.auth.login

import android.content.Intent
import android.text.InputType
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.databinding.FragmentLoginBinding
import com.tech.momenti.ui.auth.AuthCommonVM
import com.tech.momenti.ui.home_screen.HomeDashboardActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel : AuthCommonVM by viewModels()
    override fun onCreateView(view: View) {
        initOnClick()
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
                    val intent = Intent(requireContext(), HomeDashboardActivity::class.java)
                    startActivity(intent)
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


}