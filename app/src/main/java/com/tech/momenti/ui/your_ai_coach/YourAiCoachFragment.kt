package com.tech.momenti.ui.your_ai_coach

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.data.CategoryProgress
import com.tech.momenti.databinding.FragmentYourAiCoachBinding
import com.tech.momenti.ui.home_screen.insights.ProgressAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class YourAiCoachFragment : BaseFragment<FragmentYourAiCoachBinding>() {

    private val viewModel : YourAiCoachVm by viewModels()

    val data = listOf(
        CategoryProgress("Work", 60),
        CategoryProgress("Health", 20),
        CategoryProgress("Relationships", 70),
        CategoryProgress("Learning", 100),
        CategoryProgress("Mindfulness", 40)
    )


    override fun onCreateView(view: View) {

        val fullText = "Health seem low this week. \nSuggestion: Add a 10-min walk tomorrow?"

// Create spannable text
        val spannable = SpannableString(fullText)

// Find "Suggestion" word position
        val keyword = "Suggestion"
        val start = fullText.indexOf(keyword)
        val end = start + keyword.length

// Apply color only to "Suggestion"
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.yellow)),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.description.text = spannable
        initAdapter()
    }

    private fun initAdapter() {
        binding.rvLifeBalance.adapter = ProgressAdapter(data)
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_your_ai_coach
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

}