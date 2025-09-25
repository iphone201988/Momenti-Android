package com.tech.momenti.ui.common_activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.tech.momenti.R
import com.tech.momenti.base.BaseActivity
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.utils.BindingUtils
import com.tech.momenti.databinding.ActivityCommonBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CommonActivity : BaseActivity<ActivityCommonBinding>() {

    private val viewModel : CommonActivityVm  by viewModels()

    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.mainNavigationHost) as NavHostFragment).navController
    }


    override fun getLayoutResource(): Int {
          return R.layout.activity_common
    }

    override fun getViewModel(): BaseViewModel {
          return  viewModel
    }

    override fun onCreateView() {
        BindingUtils.statusBarStyle(this@CommonActivity)
        BindingUtils.statusBarTextColor(this@CommonActivity, true)

        initView()

    }

    private fun initView() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                navController.graph =
                    navController.navInflater.inflate(R.navigation.common_navigation).apply {
                        val message = intent.getStringExtra("from")
                        if (message != null) {
                            val bundle=Bundle()
                            when (message) {
                                "addTask" ->{
                                       setStartDestination(R.id.fragmentAddTask)
                                }
                                "editProfile" ->{
                                    setStartDestination(R.id.fragmentEditProfile)
                                }
                                "changePassword" ->{
                                    setStartDestination(R.id.fragmentChangePassword)
                                }
                                "share" ->{
                                    setStartDestination(R.id.fragmentShare)
                                }
                                "aiCoach" ->{
                                    setStartDestination(R.id.fragmentYourAiCoach)
                                }
                                "notification" ->{
                                    setStartDestination(R.id.fragmentNotification)
                                }
                                "addGratitude" ->{
                                    setStartDestination(R.id.fragmentAddGratitude)

                                }
                                "purchasePlan" ->{
                                    setStartDestination(R.id.fragmentSubscription)

                                }
                            }
                            navController.setGraph(this, bundle)
                        }
                    }
            }
        }
    }
}