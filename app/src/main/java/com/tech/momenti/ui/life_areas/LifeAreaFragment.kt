package com.tech.momenti.ui.life_areas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.tech.momenti.BR
import com.tech.momenti.R
import com.tech.momenti.base.BaseFragment
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.SimpleRecyclerViewAdapter
import com.tech.momenti.data.Onboarding
import com.tech.momenti.databinding.FragmentLifeAreaBinding
import com.tech.momenti.databinding.ItemLayoutLifeAreasBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.builtins.ArraySerializer

@AndroidEntryPoint
class LifeAreaFragment : BaseFragment<FragmentLifeAreaBinding>() {

    private lateinit var lifeAreaAdapter : SimpleRecyclerViewAdapter<Onboarding, ItemLayoutLifeAreasBinding>
    private var lifeAreaList = ArrayList<Onboarding>()

    private val viewModel : LifeAreaFragmentVm by viewModels()


    override fun getLayoutResource(): Int {
        return R.layout.fragment_life_area
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView(view: View) {
        getLifeAreaList()
        initAdapter()
    }

    private fun initAdapter() {
        lifeAreaAdapter = SimpleRecyclerViewAdapter(R.layout.item_layout_life_areas,
            BR.bean){ v, m, pos ->

        }
        binding.rvExample.adapter = lifeAreaAdapter
        lifeAreaAdapter.list = lifeAreaList
        lifeAreaAdapter.notifyDataSetChanged()
    }

    private fun getLifeAreaList() {
        lifeAreaList.add(Onboarding("Health"))
        lifeAreaList.add(Onboarding("Work & Productivity"))
        lifeAreaList.add(Onboarding("Relationships"))
        lifeAreaList.add(Onboarding("Learning & Growth"))
        lifeAreaList.add(Onboarding("Mindfulness & Gratitude"))
    }

}