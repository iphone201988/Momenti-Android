package com.tech.momenti.ui.auth.signup

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.databinding.FragmentAccountCreatedBinding
import com.tech.momenti.ui.auth.AuthCommonVM
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AccountCreatedFragment : BaseFragment<FragmentAccountCreatedBinding>() {

    private val viewModel : AuthCommonVM by viewModels()

    override fun onCreateView(view: View) {
        initOnClick()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when(it?.id){
                R.id.saveBtn ->{
                    findNavController().navigate(R.id.fragmentLogin)

                }
            }
        })
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_account_created
    }

    override fun getViewModel(): BaseViewModel {
    return viewModel
    }

}