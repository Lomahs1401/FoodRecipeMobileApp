package com.example.foodrecipeapp.screen.detail.overview

import com.example.foodrecipeapp.data.model.Recipe
import com.example.foodrecipeapp.utils.base.BasePresenter
import java.lang.Exception

class OverviewContract {
    /**
     * View
     */
    interface View {
        fun showRecipeOverview(recipe: Recipe)
    }

    /**
     * Presenter
     */
    interface Presenter : BasePresenter<View>
}