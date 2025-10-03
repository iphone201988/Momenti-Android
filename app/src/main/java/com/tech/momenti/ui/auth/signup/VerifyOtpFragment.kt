package com.tech.momenti.ui.auth.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.utils.BindingUtils
import com.tech.momenti.base.utils.Status
import com.tech.momenti.base.utils.showToast
import com.tech.momenti.data.api.Constants
import com.tech.momenti.data.model.SignupApiResponse
import com.tech.momenti.data.model.VerifyOtpApiResponse
import com.tech.momenti.databinding.FragmentVerifyOtpBinding
import com.tech.momenti.ui.auth.AuthCommonVM
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VerifyOtpFragment : BaseFragment<FragmentVerifyOtpBinding>() {
    private val viewModel : AuthCommonVM by viewModels()
    private lateinit var otpETs: Array<EditText?>
    var isOtpComplete = false

    private var email : String ? = null
    private var side = ""


    override fun onCreateView(view: View) {

        side =   arguments?.getString("side")?: ""
        email =  arguments?.getString("email")

        initView()
        initOnClick()
        initObserver()
    }

    private fun initObserver() {
        viewModel.observeCommon.observe(viewLifecycleOwner, Observer{
            when(it?.status){
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    hideLoading()
                    try {
                        val myDataModel : VerifyOtpApiResponse ? = BindingUtils.parseJson(it.data.toString())
                        if (myDataModel != null){
                            if (myDataModel.user != null){
                                if (myDataModel.type == 1){
                                     findNavController().navigate(R.id.fragmentDailyTask)
                                }
                                else{
                                    findNavController().navigate(R.id.fragmentNewPassword)
                                }
                            }
                        }
                    }catch (e: Exception){
                        showToast(e.message.toString())
                    }


                }
                Status.ERROR -> {
                    hideLoading()
                }
                else->{

                }
            }
        })
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_verify_otp
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel

    }
    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when(it?.id){
                R.id.signUpBtn ->{
                    val otpData =
                        "${binding.edtOtpOne.text}" + "${binding.edtOtpTwo.text}" + "${binding.edtOtpThree.text}" + "${binding.edtOtpFour.text}"

                    if (otpData.isNotEmpty()){
                        if (side == "create"){
                            val data = HashMap<String, Any>()
                            data["email"] = email.toString()
                            data["otp"]  = otpData
                            data["type"] = 1

                            viewModel.verifyOtp(data, Constants.VERIFY)

                           // findNavController().navigate(R.id.fragmentDailyTask)
                        }else{
                         //   findNavController().navigate(R.id.fragmentNewPassword)
                        }
                    }



                }
                R.id.ivBack ->{
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }


    private fun initView() {
        otpETs = arrayOf(
            binding.edtOtpOne, binding.edtOtpTwo, binding.edtOtpThree, binding.edtOtpFour
        )
        otpETs.forEachIndexed { index, editText ->
            editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int,
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if (!s.isNullOrEmpty() && index != otpETs.size - 1) {
                        otpETs[index + 1]?.requestFocus()
                    }

                    // Check if all OTP fields are filled
                    isOtpComplete = otpETs.all { it!!.text.isNotEmpty() }

                }
            })

            editText?.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                    if (editText.text.isEmpty() && index != 0) {
                        otpETs[index - 1]?.apply {
                            text?.clear()  // Clear the previous EditText before focusing
                            requestFocus()
                        }
                    }
                }
                // Check if all OTP fields are filled
                isOtpComplete = otpETs.all { it!!.text.isNotEmpty() }

                false
            }
        }
    }

}