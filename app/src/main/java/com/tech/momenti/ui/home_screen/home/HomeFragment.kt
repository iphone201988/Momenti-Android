package com.tech.momenti.ui.home_screen.home

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tech.momenti.BR
import com.tech.momenti.R


import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.SimpleRecyclerViewAdapter
import com.tech.momenti.base.utils.BindingUtils
import com.tech.momenti.base.utils.Status
import com.tech.momenti.data.DayWithWeekday
import com.tech.momenti.data.TaskData
import com.tech.momenti.data.api.Constants
import com.tech.momenti.data.model.GetProfileApiResponse
import com.tech.momenti.data.model.GetTaskApiResponse
import com.tech.momenti.databinding.FragmentHomeBinding
import com.tech.momenti.databinding.ItemLayoutSwipeBinding
import com.tech.momenti.ui.common_activity.CommonActivity

import com.tech.momenti.ui.home_screen.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale
import java.util.TimeZone


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), DaysAdapter.DayListener {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapterCalendar: DaysAdapter
    private lateinit var  taskAdapter : SimpleRecyclerViewAdapter<TaskData, ItemLayoutSwipeBinding>
    private var taskList = ArrayList<TaskData>()

    private fun getTaskList() {
        taskList.add(TaskData(1, "Need to read at least 5 pages of The Alchemist"))
        taskList.add(TaskData(2, "Lorem Ipsum is simply dummy text of the printing and typesetting industry Lorem Ipsum."))
        taskList.add(TaskData(3, "Need to read at least 5 pages of The Alchemist"))
        taskList.add(TaskData(4, "Lorem Ipsum is simply dummy text of the printing and typesetting industry Lorem Ipsum."))
        taskList.add(TaskData(5, "Need to read at least 5 pages of The Alchemist"))

    }

    override fun onCreateView(view: View) {

        viewModel.getStreak(Constants.STREAK)
        viewModel.getProfile(Constants.GET_PROFILE)
        setupCalendar()
        getTaskList()
        initAdapter()
        initObserver()
        initOnClick()
    }

    private fun initObserver() {
        viewModel.obrCommon.observe(viewLifecycleOwner, Observer{
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
                        "getTaskGratitude" ->{
                            val myDataModel : GetTaskApiResponse ? = BindingUtils.parseJson(it.data.toString())
                            if (myDataModel != null){
                                if (myDataModel.data != null){
                                    
                                }
                            }
                        }
                    }

                }
                Status.ERROR -> {
                    hideLoading()
                }
                else ->  {

                }
            }
        })


    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when(it?.id){
                R.id.tvAddTask ->{
                    val intent= Intent(requireContext(), CommonActivity::class.java)
                    intent.putExtra("from","addTask")
                    startActivity(intent)
                }
                R.id.ivNotification ->{
                    val intent= Intent(requireContext(), CommonActivity::class.java)
                    intent.putExtra("from","notification")
                    startActivity(intent)
                }
                R.id.addBtn ->{
                    val intent= Intent(requireContext(), CommonActivity::class.java)
                    intent.putExtra("from","addGratitude")
                    startActivity(intent)
                }

            }
        })
    }

    private fun initAdapter() {
        taskAdapter = SimpleRecyclerViewAdapter(R.layout.item_layout_swipe, BR.bean){ v, m, pos ->

        }
        binding.rvTasks.adapter = taskAdapter
        taskAdapter.list = taskList
        taskAdapter.notifyDataSetChanged()
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    /** For Calendar **/
    private fun setupCalendar() {
        val (daysList, currentPosition) = generateDaysForYear()

        adapterCalendar = DaysAdapter(daysList, requireContext(), this)
        binding.rvCalendar.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCalendar.adapter = adapterCalendar

        // Scroll to today
        if (currentPosition != -1) {
            (binding.rvCalendar.layoutManager as LinearLayoutManager)
                .scrollToPositionWithOffset(currentPosition, 0)
        }

        // Handle snapping/center selection after scroll
        binding.rvCalendar.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    updatePosition()
                }
            }
        })
    }

    private fun updatePosition() {
        binding.rvCalendar.post {
            val layoutManager = binding.rvCalendar.layoutManager as LinearLayoutManager
            val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
            val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
            var centrePosition = 0

            centrePosition = if ((lastVisiblePosition - firstVisiblePosition) % 2 == 0) {
                (lastVisiblePosition + firstVisiblePosition) / 2
            } else {
                (lastVisiblePosition + firstVisiblePosition + 1) / 2
            }

            adapterCalendar.updateItem(centrePosition)
        }
    }

    private fun generateDaysForYear(): Pair<List<DayWithWeekday>, Int> {
        val list = mutableListOf<DayWithWeekday>()
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val today = calendar.get(Calendar.DAY_OF_MONTH)

        var todayPosition = -1
        val maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (day in 1..maxDays) {
            calendar.set(currentYear, currentMonth, day)
            val weekday = SimpleDateFormat("EEE", Locale.getDefault()).format(calendar.time)

            val item = DayWithWeekday(
                day = day.toString(),
                weekday = weekday,
                currentYear = currentYear,
                currentMonnth = currentMonth + 1
            )
            list.add(item)

            if (day == today) todayPosition = list.lastIndex
        }

        return Pair(list, todayPosition)
    }

    override fun dayShow(day: String?, weekday: String?, currentYear: Int?, currentMonth: Int?) {
        if (day == null || weekday == null || currentYear == null || currentMonth == null) return



        // Convert to ISO 8601 format (yyyy-MM-dd'T'HH:mm:ss.SSS'Z')
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
            set(Calendar.YEAR, currentYear)
            set(Calendar.MONTH, currentMonth - 1) // Month is 0-based
            set(Calendar.DAY_OF_MONTH, day.toInt())
        }

        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        isoFormat.timeZone = TimeZone.getTimeZone("UTC")
        val dateStr = isoFormat.format(calendar.time)

        Log.d("SelectedDate", "Selected: $dateStr")

// ✅ Call your API here
        callTasksApi(dateStr)


        val selectedDate = GregorianCalendar(currentYear, currentMonth - 1, day.toInt()).time
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        val monthName = SimpleDateFormat("MMM", Locale.getDefault())
            .format(selectedDate)

        val dateShow = "$day, $monthName $currentYear"
        // binding.tvEventsDate.text = "$weekday $day, $monthName"

        // Update Add Task Section
        if (selectedDate.after(today)) {
            binding.consAddTask.visibility = View.VISIBLE
            binding.consHome.visibility = View.GONE
            binding.taskDate.text = dateShow
            binding.consStreak.visibility = View.GONE
            binding.consTrack.visibility = View.GONE
        } else {
            binding.consHome.visibility = View.VISIBLE
            binding.consAddTask.visibility = View.GONE
            binding.consStreak.visibility = View.VISIBLE
            binding.consTrack.visibility = View.VISIBLE
        }

        Log.e("date", "$currentYear $currentMonth $day $weekday $monthName")
    }

    private fun callTasksApi(dateStr: String) {
        val data = HashMap<String, Any>()
        data["date"] = dateStr
        viewModel.getTaskGratitude(data, Constants.GET_TASKS)
    }

}
