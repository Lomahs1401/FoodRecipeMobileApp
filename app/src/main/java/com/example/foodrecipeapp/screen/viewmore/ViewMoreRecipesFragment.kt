package com.example.foodrecipeapp.screen.viewmore

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.adapter.ViewMoreRecipeAdapter
import com.example.foodrecipeapp.data.model.Recipe
import com.example.foodrecipeapp.data.repo.RecipeRepo
import com.example.foodrecipeapp.data.repo.source.remote.RecipeRemoteDataSource
import com.example.foodrecipeapp.databinding.FragmentViewMoreRecipesBinding
import com.example.foodrecipeapp.listener.OnBackPressedListener
import com.example.foodrecipeapp.listener.OnRecipeItemClickListener
import com.example.foodrecipeapp.screen.detail.RecipeDetailFragment
import com.example.foodrecipeapp.utils.base.BaseViewBindingFragment
import com.example.foodrecipeapp.utils.ext.addFragment
import com.example.foodrecipeapp.utils.ext.goBackFragment

class ViewMoreRecipesFragment :
    BaseViewBindingFragment<FragmentViewMoreRecipesBinding>(),
    ViewMoreRecipesContract.View,
    OnRecipeItemClickListener {

    private lateinit var viewMoreRecipesPresenter: ViewMoreRecipesPresenter
    private var listRecipes: MutableList<Recipe>? = mutableListOf()
    private var onBackPressedListener: OnBackPressedListener? = null

    private val viewMoreRecipeAdapter: ViewMoreRecipeAdapter by lazy {
        ViewMoreRecipeAdapter(this)
    }

    override fun createBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentViewMoreRecipesBinding {
        return FragmentViewMoreRecipesBinding.inflate(inflater, container, false)
    }

    override fun initData() {
        viewMoreRecipesPresenter = ViewMoreRecipesPresenter(
            RecipeRepo.getInstanceRecipeRemoteRepo(RecipeRemoteDataSource.getInstance())
        )
        viewMoreRecipesPresenter.setView(this)
    }

    override fun initView() {
        arguments?.run {
            listRecipes = getParcelableArrayList(LIST_RECIPES)
        }
        listRecipes?.let { viewMoreRecipeAdapter.setData(it) }
        binding.rcvRecipes.adapter = viewMoreRecipeAdapter

        binding.imgBackButton.setOnClickListener {
            listRecipes?.let { it1 -> onBackPressedListener?.onBackPressedWithData(it1) }
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
            handleClickSearchRecipe(searchValue)
        }
    }

    override fun handleClickSearchRecipe(searchValue: String) {
        listRecipes?.let { viewMoreRecipesPresenter.searchRecipesInList(it, searchValue) }
    }

    override fun onSearchRecipesInList(listRecipes: MutableList<Recipe>) {
        if (listRecipes.size == 0) {
            binding.rcvRecipes.visibility = View.GONE
            binding.tvNoRecipeFound.visibility = View.VISIBLE
        } else {
            viewMoreRecipeAdapter.setData(listRecipes)
            binding.tvNoRecipeFound.visibility = View.GONE
            binding.rcvRecipes.visibility = View.VISIBLE
            binding.rcvRecipes.adapter = viewMoreRecipeAdapter
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

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        viewMoreRecipeAdapter.notifyDataSetChanged()
    }

    fun setOnBackListener(listener: OnBackPressedListener) {
        onBackPressedListener = listener
    }

    companion object {
        private const val LIST_RECIPES = "LIST_RECIPES"

        @JvmStatic
        fun newInstance(listRecipes: MutableList<Recipe>) = ViewMoreRecipesFragment().apply {
            arguments = bundleOf(LIST_RECIPES to listRecipes)
        }
    }
}
