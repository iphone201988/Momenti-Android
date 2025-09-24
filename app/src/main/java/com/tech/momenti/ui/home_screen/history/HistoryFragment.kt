package com.tech.momenti.ui.home_screen.history

import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.tech.momenti.BR
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.SimpleRecyclerViewAdapter
import com.tech.momenti.base.utils.BaseCustomBottomSheet
import com.tech.momenti.data.Gratitude
import com.tech.momenti.data.TaskData
import com.tech.momenti.data.TaskHistory
import com.tech.momenti.databinding.BottomSheetCalendarBinding
import com.tech.momenti.databinding.FragmentHistoryBinding
import com.tech.momenti.databinding.ItemLayoutGratitudeBinding
import com.tech.momenti.databinding.ItemLayoutTaskDateBinding
import com.tech.momenti.ui.common_activity.CommonActivity

import com.tech.momenti.ui.home_screen.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>() , BaseCustomBottomSheet.Listener {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var calendarBottomSheet: BaseCustomBottomSheet<BottomSheetCalendarBinding>

    private lateinit var historyTaskAdapter : SimpleRecyclerViewAdapter<TaskHistory, ItemLayoutTaskDateBinding>
    private lateinit var historyGratitudeAdapter : SimpleRecyclerViewAdapter<Gratitude, ItemLayoutGratitudeBinding>
    private var taskHistoryList = ArrayList<TaskHistory>()
    private var gratitudeList = ArrayList<Gratitude>()

    override fun onCreateView(view: View) {
        getHistoryList()
        getGratitudeList()
        initBottomSheet()
        initOnClick()
        initAdapter()
    }

    private fun getGratitudeList() {
        gratitudeList.add(Gratitude("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and it to make a type specimen book.","14, Feb 2025 "))
        gratitudeList.add(Gratitude("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and it to make a type specimen book.","14, Feb 2025 "))
        gratitudeList.add(Gratitude("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and it to make a type specimen book.","14, Feb 2025 "))
        gratitudeList.add(Gratitude("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and it to make a type specimen book.","14, Feb 2025 "))
        gratitudeList.add(Gratitude("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and it to make a type specimen book.","14, Feb 2025 "))
        gratitudeList.add(Gratitude("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and it to make a type specimen book.","14, Feb 2025 "))
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when(it?.id){
                R.id.consFilter ->{
                    calendarBottomSheet.show()
                }
                R.id.tvTasks ->{
                    binding.rvHistoryTask.visibility = View.VISIBLE
                    binding.rvGratitude.visibility = View.GONE


                    // Change style for tabs
                    binding.tvTasks.setBackgroundResource(R.drawable.button_bg)
                    binding.tvTasks.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                    binding.tvGratitude.setBackgroundResource(R.drawable.bg_stroke)
                    binding.tvGratitude.setTextColor(ContextCompat.getColor(requireContext(), R.color.app_black))

                }
                R.id.tvGratitude ->{
                    binding.rvHistoryTask.visibility = View.GONE
                    binding.rvGratitude.visibility = View.VISIBLE


                    // Change style for tabs
                    binding.tvGratitude.setBackgroundResource(R.drawable.button_bg)
                    binding.tvGratitude.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                    binding.tvTasks.setBackgroundResource(R.drawable.bg_stroke)
                    binding.tvTasks.setTextColor(ContextCompat.getColor(requireContext(), R.color.app_black))
                }

                R.id.ivNotification ->{
                    val intent= Intent(requireContext(), CommonActivity::class.java)
                    intent.putExtra("from","notification")

                    startActivity(intent)
                }
            }
        })
    }

    private fun initBottomSheet() {
        calendarBottomSheet = BaseCustomBottomSheet(requireContext() , R.layout.bottom_sheet_calendar,this)
    }

    private fun initAdapter() {
        historyTaskAdapter  = SimpleRecyclerViewAdapter(R.layout.item_layout_task_date, BR.bean){v,m,pos ->
            when(v.id){
                R.id.dropDown -> {
                    m.isExpanded = !m.isExpanded   // toggle state
                    historyTaskAdapter.notifyItemChanged(pos) // refresh only this row
                }
            }
        }
        binding.rvHistoryTask.adapter = historyTaskAdapter
        historyTaskAdapter.list =  taskHistoryList
        historyTaskAdapter.notifyDataSetChanged()

        historyGratitudeAdapter = SimpleRecyclerViewAdapter(R.layout.item_layout_gratitude,BR.bean){v,m,pos ->

        }
        binding.rvGratitude.adapter = historyGratitudeAdapter
        historyGratitudeAdapter.list = gratitudeList
        historyGratitudeAdapter.notifyDataSetChanged()
    }

    private fun getHistoryList() {
        taskHistoryList.add(
            TaskHistory(
                "14, Sep 2025",
                listOf(
                    TaskData(1, "Need to read at least 5 pages of The Alchemist"),
                    TaskData(2, "Lorem Ipsum is simply dummy data and view"),
                    TaskData(3, "Lorem Ipsum is simply dummy text...")
                )
            )
        )

        taskHistoryList.add(
            TaskHistory(
                "15, Sep 2025",
                listOf(
                    TaskData(1, "Need to read at least 5 pages of The Alchemist"),
                    TaskData(2, "Lorem Ipsum is simply dummy data and view"),
                    TaskData(3, "Lorem Ipsum is simply dummy text...")
                )
            )
        )


        taskHistoryList.add(
            TaskHistory(
                "16, Sep 2025",
                listOf(
                    TaskData(1, "Need to read at least 5 pages of The Alchemist"),
                    TaskData(2, "Lorem Ipsum is simply dummy data and view"),
                    TaskData(3, "Lorem Ipsum is simply dummy text...")
                )
            )
        )

        taskHistoryList.add(
            TaskHistory(
                "17, Sep 2025",
                listOf(
                    TaskData(1, "Need to read at least 5 pages of The Alchemist"),
                    TaskData(2, "Lorem Ipsum is simply dummy data and view"),
                    TaskData(3, "Lorem Ipsum is simply dummy text...")
                )
            )
        )
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_history
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onViewClick(view: View?) {

    }

}