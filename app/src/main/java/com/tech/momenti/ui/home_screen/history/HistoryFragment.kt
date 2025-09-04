package com.tech.momenti.ui.home_screen.history

import android.view.View
import androidx.fragment.app.viewModels
import com.tech.momenti.BR
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.SimpleRecyclerViewAdapter
import com.tech.momenti.data.TaskData
import com.tech.momenti.data.TaskHistory
import com.tech.momenti.databinding.FragmentHistoryBinding
import com.tech.momenti.databinding.ItemLayoutTaskDateBinding
import com.tech.momenti.ui.home_screen.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var historyTaskAdapter : SimpleRecyclerViewAdapter<TaskHistory, ItemLayoutTaskDateBinding>
    private var taskHistoryList = ArrayList<TaskHistory>()

    override fun onCreateView(view: View) {
        getHistoryList()
        initAdapter()
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

}