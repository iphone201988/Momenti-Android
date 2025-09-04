package com.tech.momenti.base.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tech.momenti.R
import com.google.android.material.imageview.ShapeableImageView
import com.tech.momenti.BR
import com.tech.momenti.base.SimpleRecyclerViewAdapter
import com.tech.momenti.data.NotificationData
import com.tech.momenti.data.TaskData
import com.tech.momenti.databinding.ItemLayoutNotificationDataBinding
import com.tech.momenti.databinding.ItemLayoutSwipeBinding

object BindingUtils {


    @BindingAdapter("setIcon")
    @JvmStatic
    fun setIcon(image: ImageView, i: Int) {
        image.setImageResource(i)
    }


    @BindingAdapter("tintByType")
    @JvmStatic
    fun tintByType(view: ImageView, type: Int) {
        val color = if (type == 2) {
            ContextCompat.getColor(view.context, R.color.red)   // for logout
        } else {
            ContextCompat.getColor(view.context, R.color.app_black) // default
        }
        view.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    }

    @BindingAdapter("textColorByType")
    @JvmStatic
    fun textColorByType(view: TextView, type: Int) {
        val color = if (type == 2) {
            ContextCompat.getColor(view.context, R.color.red)
        } else {
            ContextCompat.getColor(view.context, R.color.app_black)
        }
        view.setTextColor(color)
    }




    /** full screen status bar style change **/
    @SuppressLint("ObsoleteSdkInt")
    fun statusBarStyle(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            activity.window.statusBarColor = Color.TRANSPARENT
        }
    }


    /** set status bar item color change **/
    fun statusBarTextColor(activity: Activity, isDark: Boolean = false) {
        WindowCompat.setDecorFitsSystemWindows(activity.window, true)
        WindowInsetsControllerCompat(
            activity.window, activity.window.decorView
        ).isAppearanceLightStatusBars = isDark
    }


    @BindingAdapter("setImageFromUrl")
    @JvmStatic
    fun setImageFromUrl(image: ShapeableImageView, url: String?) {
        if (url != null) {
            Glide.with(image.context).load(url).placeholder(R.drawable.user)
                .error(R.drawable.user).into(image)
        }
    }


    fun styleSystemBars(activity: Activity, color: Int) {
        activity.window.navigationBarColor = color
    }

    fun statusBarStyleWhite(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            activity.window.statusBarColor = Color.TRANSPARENT
        }
    }

    fun statusBarStyleBlack(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // Ensures black text/icons
            activity.window.statusBarColor = Color.TRANSPARENT // Transparent status bar
        }
    }

    @BindingAdapter("childHistoryTaskAdapter")
    @JvmStatic
    fun childHistoryTaskAdapter(view : RecyclerView, tasks : List<TaskData>?){

        // Create and set a LayoutManager for the inner RecyclerView
        val layoutManager = LinearLayoutManager(view.context)
        view.layoutManager = layoutManager
        val context = view.context
        val taskAdapter = SimpleRecyclerViewAdapter<TaskData, ItemLayoutSwipeBinding>(R.layout.item_layout_swipe,BR.bean){ v, m, pos ->
            when(v.id){
                R.id.consMain ->{

                }
            }
        }
        view.adapter = taskAdapter
        taskAdapter.list = tasks
        taskAdapter.notifyDataSetChanged()

    }


    @BindingAdapter("childNotificationAdapter")
    @JvmStatic
    fun childNotificationAdapter(view : RecyclerView, notification : List<NotificationData>?){

        // Create and set a LayoutManager for the inner RecyclerView
        val layoutManager = LinearLayoutManager(view.context)
        view.layoutManager = layoutManager
        val context = view.context
        val notificationAdapter = SimpleRecyclerViewAdapter<NotificationData, ItemLayoutNotificationDataBinding>(R.layout.item_layout_notification_data,BR.bean){ v, m, pos ->
            when(v.id){
                R.id.consMain ->{

                }
            }
        }
        view.adapter = notificationAdapter
        notificationAdapter.list = notification
        notificationAdapter.notifyDataSetChanged()

    }

}
