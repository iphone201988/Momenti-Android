package com.tech.momenti.ui.home_screen.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tech.momenti.R
import com.tech.momenti.data.DayWithWeekday
import com.tech.momenti.databinding.ItemDayBinding
import java.util.Calendar

class DaysAdapter(
    private val items: List<DayWithWeekday>,
    private val context: Context,
    private val listener: DayListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var centerPosition = -1
    private var todayPosition = -1

    init {
        // Get today’s values
        val calendar = Calendar.getInstance()
        val todayDay = calendar.get(Calendar.DAY_OF_MONTH).toString()
        val todayMonth = calendar.get(Calendar.MONTH) + 1
        val todayYear = calendar.get(Calendar.YEAR)

        // Find today’s position in list
        todayPosition = items.indexOfFirst {
            it.day == todayDay && it.currentYear == todayYear && it.currentMonnth == todayMonth
        }

        // By default, select today
        centerPosition = todayPosition

        // Fire listener for today
        if (centerPosition != -1) {
            val todayItem = items[centerPosition]
            listener.dayShow(
                todayItem.day,
                todayItem.weekday,
                todayItem.currentYear,
                todayItem.currentMonnth
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DayViewHolder(
            ItemDayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        (holder as DayViewHolder).binding.apply {
            date.text = item.day
            day.text = item.weekday

            // Highlight selected day
            if (position == centerPosition) {
                consMain.setBackgroundResource(R.drawable.bg_selected)
                date.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                day.setTextColor(ContextCompat.getColor(context, android.R.color.white))
            } else {
                consMain.setBackgroundResource(R.drawable.date_bg)
                date.setTextColor(ContextCompat.getColor(context, R.color.app_black))
                day.setTextColor(ContextCompat.getColor(context, R.color.app_black))
            }

            // Show "Today" only for today’s date
            if (position == todayPosition) {
                tvToday.visibility = View.VISIBLE
            } else {
                tvToday.visibility = View.INVISIBLE
            }

            // Handle click
            consMain.setOnClickListener {
                updateItem(position)
                listener.dayShow(item.day, item.weekday, item.currentYear, item.currentMonnth)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateItem(position: Int) {
        centerPosition = position
        notifyDataSetChanged()
    }

    class DayViewHolder(itemView: ItemDayBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding = itemView
    }

    interface DayListener {
        fun dayShow(day: String?, weekDay: String?, currentYear: Int?, currentMonth: Int?)
    }
}
