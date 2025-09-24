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
import com.tech.momenti.databinding.FragmentSecondOnboardingBinding
import com.tech.momenti.databinding.ItemLayoutOnboardingBinding
import com.tech.momenti.databinding.ItemLayoutSolutionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class SecondOnboardingFragment : BaseFragment<FragmentSecondOnboardingBinding>() {

    private val viewModel : OnboardingVm by viewModels()
    private lateinit var  onBoardingAdapter : SimpleRecyclerViewAdapter<Onboarding, ItemLayoutSolutionBinding>

    private var onboardingList = ArrayList<Onboarding>()


    override fun getLayoutResource(): Int {
       return R.layout.fragment_second_onboarding
    }

    override fun getViewModel(): BaseViewModel {
          return viewModel
    }

    override fun onCreateView(view: View) {
        getOnboardingList()
        initAdapter()
    }

    private fun initAdapter() {
        onBoardingAdapter = SimpleRecyclerViewAdapter(R.layout.item_layout_solution, BR.bean){v,m,pos ->
            when(v.id){

            }
        }
        binding.rvProblem.adapter = onBoardingAdapter
        onBoardingAdapter.list = onboardingList
        onBoardingAdapter.notifyDataSetChanged()

    }

    private fun getOnboardingList() {
        onboardingList.add(Onboarding("Build habits that last and create momentum."))
        onboardingList.add(Onboarding("Find purpose in every day, no matter how busy life gets."))
        onboardingList.add(Onboarding("Enjoy the satisfaction of finishing what matters and getting the right things done."))
        onboardingList.add(Onboarding("Experience the joy of gratitude and living in the moment"))
        onboardingList.add(Onboarding("Find balance in achieving more, whilst appreciating what you already have"))
    }
}