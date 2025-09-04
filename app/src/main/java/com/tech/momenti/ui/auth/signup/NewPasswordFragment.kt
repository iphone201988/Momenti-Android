package com.tech.momenti.ui.auth.signup

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.databinding.FragmentNewPasswordBinding
import com.tech.momenti.ui.auth.AuthCommonVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPasswordFragment : BaseFragment<FragmentNewPasswordBinding>() {

    private val viewModel : AuthCommonVM by viewModels()

    override fun onCreateView(view: View) {
        initOnClick()
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
                    findNavController().navigate(R.id.fragmentPasswordChanged)

                }
                R.id.ivBack ->{
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }




}