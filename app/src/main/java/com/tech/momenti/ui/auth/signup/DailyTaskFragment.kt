package com.tech.momenti.ui.auth.signup

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tech.momenti.BR
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.SimpleRecyclerViewAdapter
import com.tech.momenti.data.FocusArea
import com.tech.momenti.databinding.FragmentDailyTaskBinding
import com.tech.momenti.databinding.ItemLayoutFocusAreaBinding
import com.tech.momenti.ui.auth.AuthCommonVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyTaskFragment : BaseFragment<FragmentDailyTaskBinding>() {


    private lateinit var focusAdapter : SimpleRecyclerViewAdapter<FocusArea, ItemLayoutFocusAreaBinding>
    private var focusList = ArrayList<FocusArea>()
    private val viewModel : AuthCommonVM by viewModels()


    override fun onCreateView(view: View) {
        initOnClick()
        getFocusList()
        initAdapter()
    }

    private fun getFocusList() {
        focusList.add(FocusArea("Health"))
        focusList.add(FocusArea("Work & Productivity"))
        focusList.add(FocusArea("Relationships"))
        focusList.add(FocusArea("Learning & Growth"))
        focusList.add(FocusArea("Mindfulness & Gratitude"))
    }

    private fun initAdapter() {
        focusAdapter = SimpleRecyclerViewAdapter(R.layout.item_layout_focus_area, BR.bean){v,m,pos ->
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

    override fun getLayoutResource(): Int {
        return R.layout.fragment_daily_task
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }


    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when(it?.id){
                R.id.saveBtn ->{
                    findNavController().navigate(R.id.fragmentDailyGratitude)

                }
                R.id.ivBack ->{
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
                R.id.etFocusArea ->{
                    binding.rvFocusAreas.visibility = View.VISIBLE
                }
                R.id.tvAddTask -> {
                    when {
                        binding.consTaskFour.visibility == View.GONE -> {
                            binding.consTaskFour.visibility = View.VISIBLE
                            binding.tvAddTask.text = "+ Add Task 5"
                        }
                        binding.consTaskFive.visibility == View.GONE -> {
                            binding.consTaskFive.visibility = View.VISIBLE
                            binding.tvAddTask.visibility = View.GONE // hide button after 5
                        }
                    }
                }
                R.id.tvWhatLifeArea ->{
                    findNavController().navigate(R.id.fragmentLifeArea)

                }

            }
        })
    }

}