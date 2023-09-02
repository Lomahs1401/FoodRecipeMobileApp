package com.example.foodrecipeapp.screen.detail.instruction

import com.example.foodrecipeapp.data.repo.RecipeRepo

class InstructionPresenter(
    private val recipeRepo: RecipeRepo?
) : InstructionContract.Presenter {

    private var view: InstructionContract.View? = null

    override fun onStart() {
        TODO("Not yet implemented")
    }

    override fun onStop() {
        TODO("Not yet implemented")
    }

    override fun setView(view: InstructionContract.View?) {
        this.view = view
    }
}