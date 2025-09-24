package com.tech.momenti.ui.home_screen.insights

import android.content.Intent
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
import com.tech.momenti.data.CategoryProgress
import com.tech.momenti.databinding.FragmentInsightsBinding
import com.tech.momenti.ui.common_activity.CommonActivity
import com.tech.momenti.ui.home_screen.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsightsFragment : BaseFragment<FragmentInsightsBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    val data = listOf(
        CategoryProgress("Work", 60),
        CategoryProgress("Health", 20),
        CategoryProgress("Relationships", 70),
        CategoryProgress("Learning", 100),
        CategoryProgress("Mindfulness", 40)
    )

    override fun onCreateView(view: View) {
        initOnClick()
        initAdapter()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when(it?.id){
                R.id.aiCoachBtn ->{
                    val intent= Intent(requireContext(), CommonActivity::class.java)
                    intent.putExtra("from","aiCoach")

                    startActivity(intent)
                }
                R.id.ivNotification ->{
                    val intent= Intent(requireContext(), CommonActivity::class.java)
                    intent.putExtra("from","notification")

                    startActivity(intent)
                }
            }
        })
    }

    private fun initAdapter() {
        binding.rvLifeBalance.adapter = ProgressAdapter(data)
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_insights
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

}