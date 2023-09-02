package com.example.foodrecipeapp.screen.detail.overview

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.data.model.Recipe
import com.example.foodrecipeapp.data.repo.RecipeRepo
import com.example.foodrecipeapp.data.repo.source.remote.RecipeRemoteDataSource
import com.example.foodrecipeapp.databinding.FragmentOverviewBinding
import com.example.foodrecipeapp.utils.base.BaseViewBindingFragment

class OverviewFragment(private val recipe: Recipe) :
    BaseViewBindingFragment<FragmentOverviewBinding>(), OverviewContract.View {

    private lateinit var overviewPresenter: OverviewPresenter
    override fun createBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOverviewBinding {
        return FragmentOverviewBinding.inflate(inflater, container, false)
    }

    override fun initData() {
        overviewPresenter = OverviewPresenter(
            RecipeRepo.getInstanceRecipeRemoteRepo(RecipeRemoteDataSource.getInstance())
        )
        overviewPresenter.setView(this)
    }

    override fun initView() {
        showRecipeOverview(recipe)
    }

    override fun showRecipeOverview(recipe: Recipe) {
        binding.tvOverview.text =
            HtmlCompat.fromHtml(recipe.summary, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.tvOverview.movementMethod = LinkMovementMethod.getInstance()

        binding.vegetarianCheck.setImageResource(if (recipe.vegetarian) YES_STATE else NO_STATE)
        binding.veganCheck.setImageResource(if (recipe.vegetarian) YES_STATE else NO_STATE)
        binding.glutenFreeCheck.setImageResource(if (recipe.glutenFree) YES_STATE else NO_STATE)
        binding.dairyFreeCheck.setImageResource(if (recipe.dairyFree) YES_STATE else NO_STATE)
        binding.veryHealthyCheck.setImageResource(if (recipe.veryHealthy) YES_STATE else NO_STATE)
        binding.veryPopularCheck.setImageResource(if (recipe.veryPopular) YES_STATE else NO_STATE)
        binding.cheapCheck.setImageResource(if (recipe.cheap) YES_STATE else NO_STATE)
        binding.lowFodmapCheck.setImageResource(if (recipe.lowFodmap) YES_STATE else NO_STATE)
        binding.sustainableCheck.setImageResource(if (recipe.sustainable) YES_STATE else NO_STATE)

        if (recipe.gaps == "no") {
            binding.gapsCheck.setImageResource(NO_STATE)
        } else {
            binding.gapsCheck.setImageResource(YES_STATE)
        }

        binding.tvHealthScore.text = recipe.healthScore.toString()
        binding.tvWeightWatcherSmartPoint.text = recipe.weightWatcherSmartPoints.toString()
    }

    companion object {
        private val YES_STATE = R.drawable.baseline_check_circle_24
        private val NO_STATE = R.drawable.baseline_cancel_24

        @JvmStatic
        fun newInstance(recipe: Recipe) = OverviewFragment(recipe)
    }
}
