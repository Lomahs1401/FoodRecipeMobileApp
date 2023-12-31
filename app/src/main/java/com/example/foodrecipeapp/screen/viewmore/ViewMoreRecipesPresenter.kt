package com.example.foodrecipeapp.screen.viewmore

import com.example.foodrecipeapp.data.model.Recipe
import com.example.foodrecipeapp.data.repo.FetchDataResult
import com.example.foodrecipeapp.data.repo.RecipeRepo
import com.example.foodrecipeapp.listener.OnResultListener

class ViewMoreRecipesPresenter(
    private val recipeRepo: RecipeRepo?
) : ViewMoreRecipesContract.Presenter {

    private var view: ViewMoreRecipesContract.View? = null

    override fun onStart() {
        // TODO("Not yet implemented")
    }

    override fun onStop() {
        // TODO("Not yet implemented")
    }

    override fun setView(view: ViewMoreRecipesContract.View?) {
        this.view = view
    }

    override fun searchRecipesInList(listRecipes: MutableList<Recipe>, searchValue: String) {
        recipeRepo?.searchRecipesInList(
            object :
                OnResultListener<MutableList<Recipe>> {
                override fun onSuccess(dataResult: FetchDataResult<MutableList<Recipe>>) {
                    when (dataResult) {
                        is FetchDataResult.Success -> {
                            if (dataResult.fetchDataType == FetchDataResult.FETCH_TYPE_SEARCH_RECIPES) {
                                view?.onSearchRecipesInList(dataResult.data)
                            }
                        }
                        is FetchDataResult.Error -> view?.onError(dataResult.exception)
                    }
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                }
            },
            searchValue,
            listRecipes
        )
    }
}
