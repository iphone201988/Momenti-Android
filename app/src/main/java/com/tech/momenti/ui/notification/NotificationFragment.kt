package com.tech.momenti.ui.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.tech.momenti.BR
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.SimpleRecyclerViewAdapter
import com.tech.momenti.data.Notification
import com.tech.momenti.data.NotificationData
import com.tech.momenti.data.TaskData
import com.tech.momenti.databinding.FragmentNotificationBinding
import com.tech.momenti.databinding.ItemLayoutNotificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : BaseFragment<FragmentNotificationBinding>() {

    private lateinit var notificationAdapter : SimpleRecyclerViewAdapter<Notification, ItemLayoutNotificationBinding>

    private var notificationList = ArrayList<Notification>()
    private val viewModel : NotificationVm by viewModels()


    override fun onCreateView(view: View) {
        getNotificationList()
        initOnClick()
        initAdapter()
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

    private fun initAdapter() {
        notificationAdapter = SimpleRecyclerViewAdapter(R.layout.item_layout_notification, BR.bean){v,m,pos ->

        }
        binding.rvNotification.adapter= notificationAdapter
        notificationAdapter.list = notificationList
        notificationAdapter.notifyDataSetChanged()

    }

    private fun getNotificationList() {

        notificationList.add(
            Notification("Today",
                listOf(
                    NotificationData("Add Gratitude","Add gratitude for today","10:00 PM"),
                    NotificationData("2 Task Left","Complete your pending tasks","10:00 PM"),
                    NotificationData("Streak Alert","Don’t break your streak – log today’s progress!","10:00 PM"),

                    )
            )
        )
        notificationList.add(
            Notification("Yesterday",
                listOf(
                    NotificationData("AI Coach","Health tasks are low this week – add one tomorrow?","10:00 PM"),
                    NotificationData("Daily Task Reminder","Set your Power List for the day","10:00 PM"),
                    )
            )
        )
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_notification
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

}