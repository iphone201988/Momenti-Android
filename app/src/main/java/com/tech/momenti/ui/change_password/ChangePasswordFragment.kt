package com.tech.momenti.ui.change_password

import android.text.TextUtils
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.utils.BindingUtils
import com.tech.momenti.base.utils.Status
import com.tech.momenti.base.utils.showToast
import com.tech.momenti.data.api.Constants
import com.tech.momenti.data.model.ChangePasswordApiResponse
import com.tech.momenti.databinding.FragmentChangePasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>() {

    private val viewModel : ChangePasswordVm by viewModels()

    override fun onCreateView(view: View) {
        initOnClick()
        initObserver()
    }

    private fun initObserver() {
        viewModel.observeCommon.observe(viewLifecycleOwner, Observer{
            when(it?.status){
                Status.LOADING ->  {
                    showLoading()
                }
                Status.SUCCESS -> {
                    hideLoading()
                    val myDataModel : ChangePasswordApiResponse ?= BindingUtils.parseJson(it.data.toString())
                    if (myDataModel != null){
                        if (myDataModel.user != null){
                            showToast(it.message.toString())
                            requireActivity().onBackPressedDispatcher.onBackPressed()
                        }
                    }
                }
                Status.ERROR ->  {
                    hideLoading()
                    showToast(it.message.toString())
                }
                else ->  {

                }

            }
        })
    }


    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when(it?.id){
                R.id.ivBack ->{
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
                R.id.tvUpdate ->{
                  if (isEmptyField()){
                      val data = HashMap<String, Any>()
                      data["oldPassword"] = binding.etPassword.text.toString().trim()
                      data["newPassword"] = binding.etNewPassword.text.toString().trim()
                      viewModel.changePassword(data, Constants.CHANGE_PASSWORD)
                  }
                }
            }
        })
    }


    override fun getLayoutResource(): Int {
       return R.layout.fragment_change_password
    }

    override fun getViewModel(): BaseViewModel {
         return viewModel
    }


    fun isEmptyField() : Boolean {
        var password = binding.etNewPassword.text.toString().trim()

        if (TextUtils.isEmpty(binding.etPassword.text.toString().trim())){
            showToast("Please enter password")
            return false
        }
        if (TextUtils.isEmpty(binding.etNewPassword.text.toString().trim())){
            showToast("Please enter new password")
            return false
        }
        if (TextUtils.isEmpty(binding.etConfirmPassword.text.toString().trim())){
            showToast("Please enter confirm password")
            return false
        }
        if (password != binding.etConfirmPassword.text.toString().trim()){
            showToast("Password and confirm password not matched")
            return false
        }
        return  true
    }
}