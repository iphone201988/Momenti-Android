package com.tech.momenti.ui.share

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
import com.tech.momenti.databinding.FragmentShareBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShareFragment : BaseFragment<FragmentShareBinding>() {

    private val viewModel : ShareFragmentVm  by viewModels()

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
          return R.layout.fragment_share
    }

    override fun getViewModel(): BaseViewModel {
            return viewModel
    }

}