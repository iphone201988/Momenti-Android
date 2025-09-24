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
import com.tech.momenti.BR
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.SimpleRecyclerViewAdapter
import com.tech.momenti.data.FocusArea
import com.tech.momenti.databinding.FragmentAddTaskBinding
import com.tech.momenti.databinding.ItemLayoutFocusAreaBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment : BaseFragment<FragmentAddTaskBinding>() {


    private lateinit var focusAdapter : SimpleRecyclerViewAdapter<FocusArea, ItemLayoutFocusAreaBinding>
    private var focusList = ArrayList<FocusArea>()

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

        getFocusList()
        initAdapter()


    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when(it?.id){
                R.id.ivBack ->{
                    requireActivity().onBackPressedDispatcher.onBackPressed()   
                }
                R.id.etFocusArea ->{
                    binding.rvFocusAreas.visibility = View.VISIBLE
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


    private fun getFocusList() {
        focusList.add(FocusArea("Health"))
        focusList.add(FocusArea("Work & Productivity"))
        focusList.add(FocusArea("Relationships"))
        focusList.add(FocusArea("Learning & Growth"))
        focusList.add(FocusArea("Mindfulness & Gratitude"))
    }


    private fun initAdapter() {
        focusAdapter = SimpleRecyclerViewAdapter(R.layout.item_layout_focus_area, BR.bean){ v, m, pos ->
            when(v?.id){
                R.id.consMain ->{
                    binding.rvFocusAreas.visibility = View.GONE
                    binding.etFocusArea.setText(m.focus)
                }
            }

        }
        binding.rvFocusAreas.adapter = focusAdapter
        focusAdapter.list = focusList
        focusAdapter.notifyDataSetChanged()
    }


}