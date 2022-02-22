package com.shahad.app.my_school.ui.teacher.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentTeacherHomeBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.main.MainActivity
import com.shahad.app.my_school.ui.manger.home.ClassesAdapterRecycler
import com.shahad.app.my_school.ui.manger.home.HomeMangerFragmentDirections
import com.shahad.app.my_school.util.extension.goToFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeTeacherFragment: BaseFragment<FragmentTeacherHomeBinding>() {

    override fun getLayoutId() = R.layout.fragment_teacher_home
    override val viewModel: HomeTeacherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("TAG","TEACHER")
        super.onCreate(savedInstanceState)
        observe()
    }

    override fun onStart() {
        super.onStart()
        recycler()
    }

    private fun recycler() {
        viewDataBinding.classRecycler.adapter= ClassesAdapterRecycler(emptyList(),viewModel)
    }

    private fun observe() {
        with(viewModel){
            unAuthentication.observe(this@HomeTeacherFragment){
                (requireActivity() as MainActivity).navToIdentity()
            }
            clickCreateClassEvent.observeEvent(this@HomeTeacherFragment){
                viewDataBinding.root.goToFragment(HomeTeacherFragmentDirections.actionHomeFragmentToNewClassFragment())
            }
            clickSchoolEvent.observeEvent(this@HomeTeacherFragment){
                viewDataBinding.root.goToFragment(HomeTeacherFragmentDirections.actionHomeFragmentToTeacherSchoolsFragment())
            }
            clickClassEvent.observeEvent(this@HomeTeacherFragment){ pair ->
                viewDataBinding.root.goToFragment(HomeTeacherFragmentDirections.actionHomeFragmentToClassScreenFragment(pair.first,pair.second))
            }

        }
    }

}