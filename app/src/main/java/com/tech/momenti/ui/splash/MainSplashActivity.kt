package com.tech.momenti.ui.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.tech.momenti.MainActivityVM
import com.tech.momenti.R
import com.tech.momenti.base.BaseActivity
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.databinding.ActivityMainSplashBinding
import com.tech.momenti.ui.auth.MySplashActivity
import com.tech.momenti.ui.home_screen.HomeDashboardActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainSplashActivity : BaseActivity<ActivityMainSplashBinding>() {


    private val viewModel: MainActivityVM by viewModels()
    override fun getLayoutResource(): Int {
       return R.layout.activity_main_splash
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        Handler(Looper.getMainLooper()).postDelayed({
            val loginData = sharedPrefManager.getLoginData()
            if (loginData == null){
                val intent = Intent(this, MySplashActivity::class.java)
                startActivity(intent)
                finish() // so user cannot come back to splash
            }
            else{
                val intent = Intent(this, HomeDashboardActivity::class.java)
                startActivity(intent)
                finish() // so user cannot come back to splash
            }


        }, 2000)
    }
}