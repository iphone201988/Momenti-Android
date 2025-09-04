package com.tech.momenti.ui.change_password

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.databinding.FragmentChangePasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>() {

    private val viewModel : ChangePasswordVm by viewModels()

    override fun onCreateView(view: View) {
        initOnClick()
    }


    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when(it?.id){
                R.id.ivBack ->{
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }


    override fun getLayoutResource(): Int {
       return R.layout.fragment_change_password
    }

    override fun getViewModel(): BaseViewModel {
         return viewModel
    }

}