package com.example.foodrecipeapp.screen.detail.overview

import com.example.foodrecipeapp.data.repo.RecipeRepo

class OverviewPresenter(
    private val recipeRepo: RecipeRepo?
) : OverviewContract.Presenter {

    private var view: OverviewContract.View? = null

    override fun onStart() {
        TODO("Not yet implemented")
    }

    override fun onStop() {
        TODO("Not yet implemented")
    }

    override fun setView(view: OverviewContract.View?) {
        this.view = view
    }
}