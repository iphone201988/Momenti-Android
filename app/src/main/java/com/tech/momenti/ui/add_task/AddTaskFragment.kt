package com.tech.momenti.ui.add_task

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.tech.momenti.BR
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.SimpleRecyclerViewAdapter
import com.tech.momenti.base.utils.BaseCustomBottomSheet
import com.tech.momenti.base.utils.BindingUtils
import com.tech.momenti.base.utils.Status
import com.tech.momenti.base.utils.showToast
import com.tech.momenti.data.FocusArea
import com.tech.momenti.data.api.Constants
import com.tech.momenti.data.model.AddTaskApiResponse
import com.tech.momenti.data.model.LifeAreaResponse
import com.tech.momenti.databinding.BottomSheetLifeAreaBinding
import com.tech.momenti.databinding.FragmentAddTaskBinding
import com.tech.momenti.databinding.ItemLayoutFocusAreaBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class AddTaskFragment : BaseFragment<FragmentAddTaskBinding>() , BaseCustomBottomSheet.Listener {


    private lateinit var bottomSheetLifeArea : BaseCustomBottomSheet<BottomSheetLifeAreaBinding>

    private lateinit var focusAdapter : SimpleRecyclerViewAdapter<LifeAreaResponse.Data, ItemLayoutFocusAreaBinding>
    private var focusList = ArrayList<FocusArea>()

    private var selectedEditText: AppCompatEditText? = null

    private val lifeAreaMap = HashMap<EditText, String>()

    private val viewModel : AddTaskFragmentVm by viewModels()
    override fun onCreateView(view: View) {

        initOnClick()
        initBottomSheet()

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

        initObserver()
        initAdapter()


    }

    private fun initObserver() {
        viewModel.obrCommon.observe(viewLifecycleOwner, Observer{
            when(it?.status){
                Status.LOADING ->  {
                    showLoading()
                }
                Status.SUCCESS ->  {
                    hideLoading()
                    when(it.message){
                        "getLifeArea" ->{
                            val myDataModel : LifeAreaResponse ? = BindingUtils.parseJson(it.data.toString())
                            if (myDataModel != null){
                                if (myDataModel.data != null){
                                    focusAdapter.list = myDataModel.data
                                }
                            }
                        }
                        "addTask" ->{
                            val myDataModel : AddTaskApiResponse ? = BindingUtils.parseJson(it.data.toString())
                            if (myDataModel != null){
                                if (myDataModel.data != null){
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

    private fun initBottomSheet() {
        bottomSheetLifeArea = BaseCustomBottomSheet(requireContext(), R.layout.bottom_sheet_life_area,this)
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when(it?.id){
                R.id.ivBack ->{
                    requireActivity().onBackPressedDispatcher.onBackPressed()   
                }
                R.id.etFocusArea ->{
                  //  binding.rvFocusAreas.visibility = View.VISIBLE
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
                R.id.etLifeAreaOne ->{
                    selectedEditText = binding.etLifeAreaOne
                    bottomSheetLifeArea.show()
                }
                R.id.etLifeAreaTwo ->{
                    selectedEditText = binding.etLifeAreaTwo
                    bottomSheetLifeArea.show()

                }
                R.id.etLifeAreaThree ->{
                    selectedEditText = binding.etLifeAreaThree
                    bottomSheetLifeArea.show()

                }
                R.id.etLifeAreaFour ->{
                    selectedEditText = binding.etLifeAreaFour
                    bottomSheetLifeArea.show()

                }
                R.id.etLifeAreaFive ->{
                    selectedEditText = binding.etLifeAreaFive
                    bottomSheetLifeArea.show()

                }
                R.id.addBtn ->{
                    // Get current date in yyyy-MM-dd format
                    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())


                    // Prepare request data
                    val requestData = prepareTasksData(currentDate)

                    viewModel.addTask(requestData, Constants.ADD_TASK)

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
        viewModel.getLifeArea(Constants.LIFE_AREA)
    }


    private fun prepareTasksData(date: String): HashMap<String, Any> {
        val taskList = ArrayList<HashMap<String, Any>>()

        fun addTask(titleEt: EditText?, lifeAreaEt: EditText?) {
            if (titleEt == null || lifeAreaEt == null) return

            val title = titleEt.text?.toString()?.trim().orEmpty()
            val lifeAreaId = lifeAreaMap[lifeAreaEt].orEmpty()

            if (title.isNotEmpty() && lifeAreaId.isNotEmpty()) {
                val task = HashMap<String, Any>().apply {
                    put("title", title)
                    put("lifeAreaId", lifeAreaId)
                    put("status", "win")
                }
                taskList.add(task)
            }
        }

        // Add all your EditText pairs safely
        addTask(binding.etAddTaskOne, binding.etLifeAreaOne)
        addTask(binding.etAddTaskTwo, binding.etLifeAreaTwo)
        addTask(binding.etAddTaskThree, binding.etLifeAreaThree)
        addTask(binding.etAddTaskFour, binding.etLifeAreaFour)
        addTask(binding.etAddTaskFive, binding.etLifeAreaFive)

        return hashMapOf(
            "date" to date,
            "tasks" to taskList
        )
    }




    private fun initAdapter() {
        focusAdapter = SimpleRecyclerViewAdapter(R.layout.item_layout_focus_area, BR.bean){ v, m, pos ->
            when(v?.id){
                R.id.consMain -> {
                    selectedEditText?.let { editText ->
                        editText.setText(m.lifeArea ?: "")
                        lifeAreaMap[editText] = m._id ?: ""   // ✅ Save mapping
                    }
                    bottomSheetLifeArea.dismiss()
                }

            }

        }
        bottomSheetLifeArea.binding.rvCommonSelection.adapter = focusAdapter
        focusAdapter.notifyDataSetChanged()
    }

    override fun onViewClick(view: View?) {

    }


}