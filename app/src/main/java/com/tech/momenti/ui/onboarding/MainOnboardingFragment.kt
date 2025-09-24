package com.tech.momenti.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.utils.showToast
import com.tech.momenti.databinding.FragmentMainOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainOnboardingFragment : BaseFragment<FragmentMainOnboardingBinding>() {

    private val viewModel : OnboardingVm by viewModels()
    private lateinit var adapter: ViewPageAdapter


    override fun getLayoutResource(): Int {
        return R.layout.fragment_main_onboarding
    }

    override fun getViewModel(): BaseViewModel {
    return viewModel
    }


    override fun onCreateView(view: View) {
        initOnClick()
        initAdapter()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer{
            when(it?.id){
                R.id.nextBtn ->{
                    findNavController().navigate(R.id.fragmentChoosePlan)
                }
            }
        })
    }

    private fun initAdapter() {
        adapter = ViewPageAdapter(requireActivity())
        binding.viewPager.adapter = adapter
        binding.dotsIndicator.setViewPager2(binding.viewPager)
    }

}