package com.example.foodrecipeapp.data.repo.source

import android.content.ContentResolver
import androidx.lifecycle.LifecycleOwner
import com.example.foodrecipeapp.data.model.Recipe
import com.example.foodrecipeapp.listener.OnResultListener

interface RecipeDataSource {
    /**
     * Local
     */
    interface Local {
        fun getRecipesLocal(
            contentResolver: ContentResolver,
            listener: OnResultListener<MutableList<Recipe>>
        )
    }

    /**
     * Remote
     */
    interface Remote {
        fun getRecipesRemote(listener: OnResultListener<MutableList<Recipe>>)
        fun getListFavouritesRecipes(
            listener: OnResultListener<MutableList<Recipe>>,
            viewLifecycleOwner: LifecycleOwner
        )
        fun searchRecipesRemote(
            listener: OnResultListener<MutableList<Recipe>>,
            searchValue: String
        )
        fun searchRecipesInList(
            listener: OnResultListener<MutableList<Recipe>>,
            searchValue: String,
            listRecipes: MutableList<Recipe>
        )
        fun searchRecentRecipesInList(
            listener: OnResultListener<MutableList<Recipe>>,
            searchValue: String,
            listRecentRecipes: MutableList<Recipe>
        )
        fun filterRecipesByCategoryInList(
            listener: OnResultListener<MutableList<Recipe>>,
            searchValue: String,
            listRecentRecipes: MutableList<Recipe>
        )
    }
}
