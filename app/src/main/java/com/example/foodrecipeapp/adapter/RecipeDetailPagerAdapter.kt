package com.example.foodrecipeapp.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.foodrecipeapp.screen.detail.RecipeDetailFragment

class RecipeDetailPagerAdapter(
    fragment: RecipeDetailFragment,
    private val fragmentList: List<Fragment>
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}