package com.tech.momenti.ui.subscription

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.databinding.FragmentSubscriptionBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SubscriptionFragment : BaseFragment<FragmentSubscriptionBinding>() {

    private val viewModel : SubscriptionVm  by viewModels()


    override fun getLayoutResource(): Int {
      return R.layout.fragment_subscription
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {

        initOnCLick()

    }

    private fun initOnCLick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer{
            when(it?.id){
                R.id.ivBack ->{
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

}