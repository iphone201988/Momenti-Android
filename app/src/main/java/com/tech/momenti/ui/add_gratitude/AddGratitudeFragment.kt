package com.tech.momenti.ui.add_gratitude

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
import com.tech.momenti.databinding.FragmentAddGratitudeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddGratitudeFragment : BaseFragment<FragmentAddGratitudeBinding>() {
    private val viewModel : AddGratitudeVm by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.fragment_add_gratitude
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        viewModel.onClick.observe(viewLifecycleOwner , Observer{
            when(it?.id){
                R.id.tvAddGratitude -> {
                    when {
                        binding.consFour.visibility == View.GONE -> {
                            binding.consFour.visibility = View.VISIBLE
                            binding.tvAddGratitude.text = "+ Add Task 5"
                        }
                        binding.consFive.visibility == View.GONE -> {
                            binding.consFive.visibility = View.VISIBLE
                            binding.tvAddGratitude.visibility = View.GONE // hide button after 5
                        }
                    }
                }
                R.id.ivBack ->{
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }

            }

        })
    }

}