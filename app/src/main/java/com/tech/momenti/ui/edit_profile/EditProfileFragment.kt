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
import com.tech.momenti.base.utils.BindingUtils
import com.tech.momenti.base.utils.Status
import com.tech.momenti.base.utils.showToast
import com.tech.momenti.data.api.Constants
import com.tech.momenti.data.model.GetProfileApiResponse
import com.tech.momenti.data.model.UpdateProfileApiResponse
import com.tech.momenti.databinding.FragmentEditProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>() {

    private val viewModel : EditProfileVm by viewModels()


    override fun onCreateView(view: View) {

          viewModel.getProfile(Constants.GET_PROFILE)
          initObserver()
          initOnClick()

    }

    private fun initObserver() {
       viewModel.obrCommon.observe(viewLifecycleOwner,Observer{
           when(it?.status){
               Status.LOADING -> {
                   showLoading()
               }
               Status.SUCCESS -> {
                   hideLoading()
                   when(it.message){
                       "getProfile" ->{
                           val myDataModel : GetProfileApiResponse ? = BindingUtils.parseJson(it.data.toString())
                           if (myDataModel != null){
                               if (myDataModel.user != null){
                                   binding.bean = myDataModel.user
                               }
                           }
                       }
                       "updateProfile" ->{
                           val myDataModel : UpdateProfileApiResponse ?= BindingUtils.parseJson(it.data.toString())
                           if (myDataModel != null){
                               if (myDataModel.user != null){
                                   requireActivity().onBackPressedDispatcher.onBackPressed()
                               }
                           }
                       }

                   }
               }
               Status.ERROR -> {
                   hideLoading()
                   showToast(it.message.toString())
               }
               else -> {

               }
           }
       })
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
                R.id.tvUpdate ->{
                    val data = HashMap<String, Any>()
                    data["name"] = binding.etName.text.toString().trim()
                    viewModel.updateProfile(data, Constants.UPDATE_PROFILE)
                }
            }
        })
    }
}