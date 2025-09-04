package com.tech.momenti.ui.auth.signup

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.databinding.FragmentVerifyOtpBinding
import com.tech.momenti.ui.auth.AuthCommonVM
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VerifyOtpFragment : BaseFragment<FragmentVerifyOtpBinding>() {
    private val viewModel : AuthCommonVM by viewModels()

    private var side = ""


    override fun onCreateView(view: View) {

        side =   arguments?.getString("side")?: ""
        initOnClick()
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_verify_otp
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel

    }
    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when(it?.id){
                R.id.signUpBtn ->{
                    if (side == "create"){
                        findNavController().navigate(R.id.fragmentDailyTask)
                    }else{
                        findNavController().navigate(R.id.fragmentNewPassword)
                    }


                }
                R.id.ivBack ->{
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

}