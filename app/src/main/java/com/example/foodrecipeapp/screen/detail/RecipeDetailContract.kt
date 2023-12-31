package com.example.foodrecipeapp.screen.detail

import com.example.foodrecipeapp.data.model.Recipe
import com.example.foodrecipeapp.utils.base.BasePresenter
import java.lang.Exception

class RecipeDetailContract {
    /**
     * View
     */
    interface View {
        fun showRecipeDetailInfo(recipe: Recipe)
        fun onError(exception: Exception?)
    }

    /**
     * Presenter
     */
    interface Presenter : BasePresenter<View> {

    }
}
