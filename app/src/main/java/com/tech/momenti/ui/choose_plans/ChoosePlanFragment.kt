package com.tech.momenti.ui.choose_plans

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
import com.tech.momenti.databinding.FragmentChoosePlanBinding
import com.tech.momenti.ui.onboarding.OnboardingVm
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class ChoosePlanFragment : BaseFragment<FragmentChoosePlanBinding>() {

    private val viewModel : ChoosePlanVm by viewModels()


    override fun getLayoutResource(): Int {
          return R.layout.fragment_choose_plan
    }

    override fun getViewModel(): BaseViewModel {
          return viewModel
    }

    override fun onCreateView(view: View) {
        viewModel.onClick.observe(viewLifecycleOwner, Observer{
            when(it?.id){
                R.id.login ->{
                    findNavController().navigate(R.id.fragmentLogin)

                }
            }
        })

    }

}