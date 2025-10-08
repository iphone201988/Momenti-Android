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
import com.tech.momenti.base.utils.BindingUtils
import com.tech.momenti.base.utils.Status
import com.tech.momenti.base.utils.showToast
import com.tech.momenti.data.GratitudeItem
import com.tech.momenti.data.GratitudeRequest
import com.tech.momenti.data.api.Constants
import com.tech.momenti.data.model.AddGratitudeApiResponse
import com.tech.momenti.databinding.FragmentAddGratitudeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

        binding.date.text = SimpleDateFormat("d, MMM yyyy", Locale.getDefault()).format(Date())

        initOnClick()
        initObserver()

    }

    private fun initObserver() {
        viewModel.observeCommon.observe(viewLifecycleOwner, Observer{
            viewModel.observeCommon.observe(viewLifecycleOwner, Observer{
                when(it?.status){
                    Status.LOADING -> {
                        showLoading()
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        val myDataModel : AddGratitudeApiResponse ? = BindingUtils.parseJson(it.data.toString())
                        if (myDataModel != null){
                            if (myDataModel.data != null){
                                requireActivity().onBackPressedDispatcher.onBackPressed()
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
        })
    }

    private fun initOnClick() {
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
                R.id.saveBtn -> {
                    // Get current date in yyyy-MM-dd format
                    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                    // List of all EditTexts
                    val editTexts = listOf(
                        binding.etAddGratitudeOne,
                        binding.etAddGratitudeTwo,
                        binding.etAddGratitudeThree,
                        binding.etAddGratitudeFour,
                        binding.etAddGratitudeFive
                    )

                    // Build gratitudes list (only non-empty)
                    val gratitudesList = editTexts.mapNotNull { et ->
                        val text = et.text.toString().trim()
                        if (text.isNotEmpty()) mapOf("text" to text) else null
                    }

                    if (gratitudesList.isEmpty()) {
                        showToast( "Please enter at least one gratitude")
                    }

                    // Prepare HashMap<String, Any>
                    val data = HashMap<String, Any>()
                    data["date"] = currentDate
                    data["gratitudes"] = gratitudesList

                    // Call your existing addGratitude function
                    val url = Constants.ADD_GRATITUDE
                    viewModel.addGratitude(data, url)
                }



            }

        })
    }

}