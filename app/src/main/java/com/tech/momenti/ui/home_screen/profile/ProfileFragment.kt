package com.tech.momenti.ui.home_screen.profile

import android.content.Intent
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
import com.tech.momenti.data.ProfileData
import com.tech.momenti.data.TaskData
import com.tech.momenti.databinding.FragmentProfileBinding
import com.tech.momenti.databinding.ItemLayoutProfileBinding
import com.tech.momenti.databinding.ItemLayoutSwipeBinding
import com.tech.momenti.ui.common_activity.CommonActivity
import com.tech.momenti.ui.home_screen.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private lateinit var  profileAdapter : SimpleRecyclerViewAdapter<ProfileData, ItemLayoutProfileBinding>

    private var profileList = ArrayList<ProfileData>()
    private val viewModel: HomeViewModel by viewModels()


    override fun onCreateView(view: View) {
        getProfileList()
        initOnClick()
        initAdapter()
    }

    private fun initOnClick() {
        viewModel.onClick.observe(viewLifecycleOwner, Observer {
            when(it?.id){
                R.id.ivNotification ->{
                    val intent= Intent(requireContext(), CommonActivity::class.java)
                    intent.putExtra("from","notification")

                    startActivity(intent)
                }
            }
        })
    }

    private fun initAdapter() {
        profileAdapter = SimpleRecyclerViewAdapter(R.layout.item_layout_profile,BR.bean){v,m,pos ->
            when(v.id){
                R.id.consMain ->{
                    when(m.title){
                        "Edit Profile" ->{
                            val intent= Intent(requireContext(), CommonActivity::class.java)
                            intent.putExtra("from","editProfile")

                            startActivity(intent)
                        }
                        "Change Password" ->{
                            val intent= Intent(requireContext(), CommonActivity::class.java)
                            intent.putExtra("from","changePassword")

                            startActivity(intent)
                        }
                        "Share with Friends" ->{
                            val intent= Intent(requireContext(), CommonActivity::class.java)
                            intent.putExtra("from","share")

                            startActivity(intent)

                        }
                        "Purchase Plan" ->{
                            val intent= Intent(requireContext(), CommonActivity::class.java)
                            intent.putExtra("from","purchasePlan")

                            startActivity(intent)
                        }
                    }
                }
            }

        }
        binding.rvProfile.adapter = profileAdapter
        profileAdapter.list = profileList
        profileAdapter.notifyDataSetChanged()
    }

    private fun getProfileList() {
        profileList.add(ProfileData(R.drawable.iv_edit_profile,"Edit Profile", 1,1))
        profileList.add(ProfileData(R.drawable.iv_change_password,"Change Password", 1,1))
        profileList.add(ProfileData(R.drawable.iv_bell,"Notifications", 2,1))
        profileList.add(ProfileData(R.drawable.iv_empty_star,"Purchase Plan", 1,1))
        profileList.add(ProfileData(R.drawable.iv_share,"Share with Friends", 1,1))
        profileList.add(ProfileData(R.drawable.iv_support,"Support", 1,1))
        profileList.add(ProfileData(R.drawable.iv_logout,"Logout", 3,2))
    }

    override fun getLayoutResource(): Int {
          return R.layout.fragment_profile
    }

    override fun getViewModel(): BaseViewModel {
       return viewModel
    }

}
