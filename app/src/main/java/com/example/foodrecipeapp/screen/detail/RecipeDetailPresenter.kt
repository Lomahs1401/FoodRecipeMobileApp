package com.example.foodrecipeapp.screen.detail

import com.example.foodrecipeapp.data.repo.RecipeRepo

class RecipeDetailPresenter(
    private val recipeRepo: RecipeRepo?
) : RecipeDetailContract.Presenter {

    private var view: RecipeDetailContract.View? = null

    override fun onStart() {
        // TODO("Not yet implemented")
    }

    override fun onStop() {
        // TODO("Not yet implemented")
    }

    override fun setView(view: RecipeDetailContract.View?) {
        this.view = view
    }
}
