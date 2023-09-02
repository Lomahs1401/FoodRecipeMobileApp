package com.example.foodrecipeapp.screen.viewmore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.adapter.ViewMoreRecentRecipeAdapter
import com.example.foodrecipeapp.data.model.Recipe
import com.example.foodrecipeapp.data.repo.RecipeRepo
import com.example.foodrecipeapp.data.repo.source.remote.RecipeRemoteDataSource
import com.example.foodrecipeapp.databinding.FragmentViewMoreRecentRecipesBinding
import com.example.foodrecipeapp.listener.OnRecipeItemClickListener
import com.example.foodrecipeapp.screen.detail.RecipeDetailFragment
import com.example.foodrecipeapp.utils.base.BaseViewBindingFragment
import com.example.foodrecipeapp.utils.ext.addFragment
import com.example.foodrecipeapp.utils.ext.goBackFragment

class ViewMoreRecentRecipesFragment :
    BaseViewBindingFragment<FragmentViewMoreRecentRecipesBinding>(),
    ViewMoreRecentRecipesContract.View,
    OnRecipeItemClickListener {

    private lateinit var viewMoreRecentRecipesPresenter: ViewMoreRecentRecipesPresenter
    private var listRecentRecipes: MutableList<Recipe>? = mutableListOf()

    private val viewMoreRecentRecipeAdapter: ViewMoreRecentRecipeAdapter by lazy {
        ViewMoreRecentRecipeAdapter(this)
    }

    override fun createBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentViewMoreRecentRecipesBinding {
        return FragmentViewMoreRecentRecipesBinding.inflate(inflater, container, false)
    }

    override fun initData() {
        viewMoreRecentRecipesPresenter = ViewMoreRecentRecipesPresenter(
            RecipeRepo.getInstanceRecipeRemoteRepo(RecipeRemoteDataSource.getInstance())
        )
        viewMoreRecentRecipesPresenter.setView(this)
    }

    override fun initView() {
        arguments?.run {
            listRecentRecipes = getParcelableArrayList(LIST_RECENT_RECIPES)
        }
        listRecentRecipes?.let { viewMoreRecentRecipeAdapter.setData(it) }
        binding.rcvRecentRecipes.adapter = viewMoreRecentRecipeAdapter

        binding.imgBackButton.setOnClickListener {
            goBackFragment()
        }

        var searchValue = ""

        binding.searchRecipe.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val nonNullText: String = newText ?: ""
                searchValue = nonNullText
                return true
            }
        })

        binding.btnSearch.setOnClickListener {
            handleClickSearchRecentRecipe(searchValue)
        }
    }

    override fun handleClickSearchRecentRecipe(searchValue: String) {
        listRecentRecipes?.let {
            viewMoreRecentRecipesPresenter.searchRecentRecipesInList(
                it,
                searchValue
            )
        }
    }

    override fun onSearchRecentRecipesInList(listRecentRecipes: MutableList<Recipe>) {
        if (listRecentRecipes.size == 0) {
            binding.rcvRecentRecipes.visibility = View.GONE
            binding.tvNoRecentRecipeFound.visibility = View.VISIBLE
        } else {
            viewMoreRecentRecipeAdapter.setData(listRecentRecipes)
            binding.tvNoRecentRecipeFound.visibility = View.GONE
            binding.rcvRecentRecipes.visibility = View.VISIBLE
            binding.rcvRecentRecipes.adapter = viewMoreRecentRecipeAdapter
        }
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(context, exception?.message, Toast.LENGTH_SHORT).show()
    }

    override fun onRecipeImageClick(recipe: Recipe) {
        addFragment(
            R.id.fragment_view_more_recipes_container,
            RecipeDetailFragment.newInstance(recipe),
            true
        )
    }

    companion object {
        private const val LIST_RECENT_RECIPES = "LIST_RECENT_RECIPES"

        @JvmStatic
        fun newInstance(listRecentRecipes: MutableList<Recipe>) =
            ViewMoreRecentRecipesFragment().apply {
                arguments = bundleOf(LIST_RECENT_RECIPES to listRecentRecipes)
            }
    }
}
