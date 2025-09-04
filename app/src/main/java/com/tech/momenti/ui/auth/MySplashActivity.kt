package com.tech.momenti.ui.auth

import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.tech.momenti.R
import com.tech.momenti.base.BaseActivity
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.utils.BindingUtils
import com.tech.momenti.databinding.ActivityMySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MySplashActivity : BaseActivity<ActivityMySplashBinding>() {
    private val viewModel: AuthCommonVM by viewModels()

    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.mainNavigationHost) as NavHostFragment).navController
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_my_splash
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        initView()
        initOnClick()
    }

    private fun initOnClick() {

    }

    private fun initView() {
        BindingUtils.statusBarStyle(this@MySplashActivity)
        BindingUtils.statusBarTextColor(this@MySplashActivity, true)


        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                navController.graph =
                    navController.navInflater.inflate(R.navigation.auth_navigation).apply {
                    setStartDestination(R.id.fragmentLogin)

                    }
            }
        }
    }

}