package com.tech.momenti

import android.view.View
import androidx.activity.viewModels
import com.tech.momenti.base.BaseActivity
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.SimpleRecyclerViewAdapter
import com.tech.momenti.base.utils.Status
import com.tech.momenti.base.utils.showErrorToast
import com.tech.momenti.base.utils.showSuccessToast
import com.tech.momenti.data.model.DummyApiItem
import com.tech.momenti.databinding.ActivityMainBinding
import com.tech.momenti.databinding.HolderDummyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val viewModel: MainActivityVM by viewModels()
    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        initView()
        initOnClick()
        initObservers()
    }

    private fun initView() {
        binding.ivNoDataFound.visibility = View.VISIBLE
        binding.rvMAinActivity.visibility = View.GONE

        initAdapter()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(this) {
            when (it?.id) {
                R.id.tvClickMe -> {
                    //viewModel.getDummyList()
                }
            }
        }
    }

    private fun initObservers() {
        viewModel.observeCommon.observe(this) {
            when (it?.status) {
                Status.LOADING -> {
//                    showLoading("Loading")
                }

                Status.SUCCESS -> {
                    hideLoading()
                    it.data.let {
                        // adapter.list = it1
                        binding.ivNoDataFound.visibility = View.GONE
                        binding.tvClickMe.visibility = View.GONE
                        binding.rvMAinActivity.visibility = View.VISIBLE
                    }
                }

                Status.ERROR -> {
                    hideLoading()
                    showErrorToast(it.message.toString())
                }

                else -> {}
            }
        }
    }

    private lateinit var adapter: SimpleRecyclerViewAdapter<DummyApiItem, HolderDummyBinding>
    private fun initAdapter() {
        adapter = SimpleRecyclerViewAdapter(R.layout.holder_dummy, BR.bean) { _, m, _ ->
            showSuccessToast(m.title)
        }
        binding.rvMAinActivity.adapter = adapter
    }
}