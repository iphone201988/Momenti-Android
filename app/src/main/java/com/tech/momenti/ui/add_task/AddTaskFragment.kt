package com.tech.momenti.ui.add_task

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
import androidx.lifecycle.Observer
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.databinding.FragmentAddTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment : BaseFragment<FragmentAddTaskBinding>() {

    private val viewModel : AddTaskFragmentVm by viewModels()
    override fun onCreateView(view: View) {

        initOnClick()

        val fullText = "No actions logged in ‘Health’ this week. Suggestion: Add a 10-min walk tomorrow?"

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
        return R.layout.fragment_add_task
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

}