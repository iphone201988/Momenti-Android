package com.tech.momenti.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.tech.momenti.BR
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.SimpleRecyclerViewAdapter
import com.tech.momenti.data.Onboarding
import com.tech.momenti.databinding.FragmentFirstOnboardingBinding
import com.tech.momenti.databinding.ItemLayoutOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FirstOnboardingFragment : BaseFragment<FragmentFirstOnboardingBinding>() {

    private lateinit var  onBoardingAdapter : SimpleRecyclerViewAdapter<Onboarding, ItemLayoutOnboardingBinding>

    private var onboardingList = ArrayList<Onboarding>()
    private val viewModel : OnboardingVm by viewModels()
    override fun getLayoutResource(): Int {
        return R.layout.fragment_first_onboarding
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        getOnboardingList()
        initAdapter()
    }

    private fun initAdapter() {
        onBoardingAdapter = SimpleRecyclerViewAdapter(R.layout.item_layout_onboarding, BR.bean){v,m,pos ->
            when(v.id){

            }
        }
        binding.rvProblem.adapter = onBoardingAdapter
        onBoardingAdapter.list = onboardingList
        onBoardingAdapter.notifyDataSetChanged()

    }

    private fun getOnboardingList() {
        onboardingList.add(Onboarding("Feeling purposeless, restless, or unmotivated?"))
        onboardingList.add(Onboarding("Constantly putting off hard tasks and avoiding challenges?"))
        onboardingList.add(Onboarding("Stuck in negative thinking, missing the good around you?"))
        onboardingList.add(Onboarding("Letting days slip by — not present, not seizing the moment?"))
    }

}