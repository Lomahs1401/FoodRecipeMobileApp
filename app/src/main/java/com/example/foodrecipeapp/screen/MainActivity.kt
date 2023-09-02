package com.example.foodrecipeapp.screen

import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.adapter.MainPagerAdapter
import com.example.foodrecipeapp.animation.ZoomOutPageTransformer
import com.example.foodrecipeapp.databinding.ActivityMainBinding
import com.example.foodrecipeapp.screen.favourites.FavouriteFragment
import com.example.foodrecipeapp.screen.home.HomeFragment
import com.example.foodrecipeapp.screen.onboarding.OnBoardingActivity
import com.example.foodrecipeapp.screen.settings.SettingFragment
import com.example.foodrecipeapp.utils.DataLocalManager
import com.example.foodrecipeapp.utils.base.BaseViewBindingActivity

class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {

    private val pagerAdapter: MainPagerAdapter by lazy {
        MainPagerAdapter(this, getFragmentList())
    }

    override fun createBindingActivity(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        if (!DataLocalManager.getFirstInstalled()) {
            DataLocalManager.setFirstInstalled(true)
            Intent(this, OnBoardingActivity::class.java).also {
                startActivity(it)
            }
        } else {
            binding.viewPager.adapter = pagerAdapter
            binding.viewPager.isUserInputEnabled = false
            binding.viewPager.setPageTransformer(
                ZoomOutPageTransformer(
                    ActivityMainBinding.inflate(
                        layoutInflater
                    )
                )
            )

            binding.bottomNav.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_home -> binding.viewPager.currentItem = 0
                    R.id.action_favourite -> binding.viewPager.currentItem = 1
                    R.id.action_setting -> binding.viewPager.currentItem = 2
                }

                return@setOnItemSelectedListener true
            }
        }
    }

    override fun initData() {
        // TODO("Not yet implemented")
    }

    private fun getFragmentList(): List<Fragment> {
        return listOf(
            HomeFragment.newInstance(),
            FavouriteFragment.newInstance(),
            SettingFragment.newInstance()
        )
    }
}
