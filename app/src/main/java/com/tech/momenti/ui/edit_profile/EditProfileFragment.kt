package com.tech.momenti.ui.edit_profile

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
import com.tech.momenti.databinding.FragmentEditProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>() {

    private val viewModel : EditProfileVm by viewModels()


    override fun onCreateView(view: View) {
          initOnClick()
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_edit_profile
    }

    override fun getViewModel(): BaseViewModel {
         return viewModel
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
}