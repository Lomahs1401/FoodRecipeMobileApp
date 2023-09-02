package com.example.foodrecipeapp.screen.detail.instruction

import com.example.foodrecipeapp.data.model.Recipe
import com.example.foodrecipeapp.utils.base.BasePresenter
import java.lang.Exception

class InstructionContract {
    /**
     * View
     */
    interface View {
        fun showRecipeInstruction(recipe: Recipe)
    }

    /**
     * Presenter
     */
    interface Presenter : BasePresenter<View>
}