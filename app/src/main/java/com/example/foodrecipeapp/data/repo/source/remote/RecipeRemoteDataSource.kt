package com.example.foodrecipeapp.data.repo.source.remote

import androidx.lifecycle.LifecycleOwner
import com.example.foodrecipeapp.constant.Constant
import com.example.foodrecipeapp.data.model.Recipe
import com.example.foodrecipeapp.data.model.RecipeEntry
import com.example.foodrecipeapp.data.repo.FetchDataResult
import com.example.foodrecipeapp.data.repo.source.RecipeDataSource
import com.example.foodrecipeapp.data.repo.source.remote.fetchjson.GetJsonFromUrl
import com.example.foodrecipeapp.listener.OnResultListener
import com.example.foodrecipeapp.utils.DataLocalManager

class RecipeRemoteDataSource : RecipeDataSource.Remote {
    override fun getRecipesRemote(listener: OnResultListener<MutableList<Recipe>>) {
        GetJsonFromUrl(
            urlString = Constant.BASE_URL + Constant.BASE_URL_RECIPE,
            keyEntity = RecipeEntry.RECIPES_OBJECT
        ).callApi(listener)
    }

    override fun getListFavouritesRecipes(
        listener: OnResultListener<MutableList<Recipe>>,
        viewLifecycleOwner: LifecycleOwner
    ) {
        DataLocalManager.favouriteRecipesLiveData.observe(viewLifecycleOwner) { favourites ->
            val fetchDataResult =
                FetchDataResult.Success(
                    favourites as MutableList<Recipe>,
                    FetchDataResult.FETCH_TYPE_FAVOURITE_RECIPES
                )
            listener.onSuccess(fetchDataResult)
        }
    }

    override fun searchRecipesRemote(
        listener: OnResultListener<MutableList<Recipe>>,
        searchValue: String
    ) {
        GetJsonFromUrl(
            urlString = Constant.BASE_URL + Constant.BASE_URL_RECIPE,
            keyEntity = RecipeEntry.RECIPES_OBJECT,
            searchValue = searchValue
        ).searchRecipes(listener)
    }

    override fun searchRecipesInList(
        listener: OnResultListener<MutableList<Recipe>>,
        searchValue: String,
        listRecipes: MutableList<Recipe>
    ) {
        if (searchValue.trim().isEmpty()) {
            val result: FetchDataResult<MutableList<Recipe>> = FetchDataResult.Success(
                listRecipes.toMutableList(),
                FetchDataResult.FETCH_TYPE_SEARCH_RECIPES
            )
            listener.onSuccess(result)
        } else {
            val filterRecipes = listRecipes.filter { recipe ->
                recipe.title.contains(searchValue, ignoreCase = true)
            }
            val result: FetchDataResult<MutableList<Recipe>> = FetchDataResult.Success(
                filterRecipes.toMutableList(),
                FetchDataResult.FETCH_TYPE_SEARCH_RECIPES
            )
            listener.onSuccess(result)
        }
    }

    override fun searchRecentRecipesInList(
        listener: OnResultListener<MutableList<Recipe>>,
        searchValue: String,
        listRecentRecipes: MutableList<Recipe>
    ) {
        if (searchValue.trim().isEmpty()) {
            val result: FetchDataResult<MutableList<Recipe>> = FetchDataResult.Success(
                listRecentRecipes.toMutableList(),
                FetchDataResult.FETCH_TYPE_SEARCH_RECENT_RECIPES
            )
            listener.onSuccess(result)
        } else {
            val filterRecipes = listRecentRecipes.filter { recipe ->
                recipe.title.contains(searchValue, ignoreCase = true)
            }
            val result: FetchDataResult<MutableList<Recipe>> = FetchDataResult.Success(
                filterRecipes.toMutableList(),
                FetchDataResult.FETCH_TYPE_SEARCH_RECENT_RECIPES
            )
            listener.onSuccess(result)
        }
    }

    override fun filterRecipesByCategoryInList(
        listener: OnResultListener<MutableList<Recipe>>,
        searchValue: String,
        listRecentRecipes: MutableList<Recipe>
    ) {
        if (searchValue.trim().isEmpty()) {
            val result: FetchDataResult<MutableList<Recipe>> = FetchDataResult.Success(
                listRecentRecipes.toMutableList(),
                FetchDataResult.FETCH_TYPE_FILTER_FAVOURITE_RECIPES
            )
            listener.onSuccess(result)
        } else {
            val filterRecipes = listRecentRecipes.filter { recipe ->
                recipe.dishTypes.any { dishType ->
                    dishType.contains(searchValue, ignoreCase = true)
                }
            }
            val result: FetchDataResult<MutableList<Recipe>> = FetchDataResult.Success(
                filterRecipes.toMutableList(),
                FetchDataResult.FETCH_TYPE_FILTER_FAVOURITE_RECIPES
            )
            listener.onSuccess(result)
        }
    }

    companion object {
        private var instance: RecipeRemoteDataSource? = null

        fun getInstance() = synchronized(this) {
            instance ?: RecipeRemoteDataSource().also { instance = it }
        }
    }
}
