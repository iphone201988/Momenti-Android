package com.tech.momenti.ui.home_screen

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.tech.momenti.R
import com.tech.momenti.base.BaseActivity
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.utils.BindingUtils
import com.tech.momenti.databinding.ActivityHomeDashboardBinding
import com.tech.momenti.ui.home_screen.history.HistoryFragment
import com.tech.momenti.ui.home_screen.home.HomeFragment
import com.tech.momenti.ui.home_screen.insights.InsightsFragment
import com.tech.momenti.ui.home_screen.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDashboardActivity : BaseActivity<ActivityHomeDashboardBinding>() {


    private val viewModel : HomeViewModel by viewModels()

    override fun getLayoutResource(): Int {
      return R.layout.activity_home_dashboard
    }

    override fun getViewModel(): BaseViewModel {
           return viewModel
    }

    override fun onCreateView() {
        BindingUtils.statusBarStyle(this@HomeDashboardActivity)
        BindingUtils.statusBarTextColor(this@HomeDashboardActivity, true)
        loadFragment(HomeFragment())
        bottomNavView()
    }



    private fun bottomNavView() {
        binding.bottomNav.itemIconTintList = null
        binding.bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.home ->{
                    loadFragment(HomeFragment())
                    true
                }
                R.id.history ->{
                    loadFragment(HistoryFragment())
                    true
                }
                R.id.profile ->{
                    loadFragment(ProfileFragment())
                    true
                }
                R.id.insight ->{
                    loadFragment(InsightsFragment())
                    true
                }

                else ->{
                    false
                }
            }
        }
    }


    fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressedDispatcher.onBackPressed()
        }
        finishAffinity()
    }


}