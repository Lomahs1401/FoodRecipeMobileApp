package com.example.foodrecipeapp.screen.home

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.adapter.HomeChildAdapter
import com.example.foodrecipeapp.data.model.HomeChild
import com.example.foodrecipeapp.data.model.Recipe
import com.example.foodrecipeapp.data.repo.RecipeRepo
import com.example.foodrecipeapp.data.repo.source.remote.RecipeRemoteDataSource
import com.example.foodrecipeapp.databinding.FragmentHomeBinding
import com.example.foodrecipeapp.listener.OnBackPressedListener
import com.example.foodrecipeapp.listener.OnRecipeItemClickListener
import com.example.foodrecipeapp.screen.detail.RecipeDetailFragment
import com.example.foodrecipeapp.screen.viewmore.ViewMoreRecentRecipesFragment
import com.example.foodrecipeapp.screen.viewmore.ViewMoreRecipesFragment
import com.example.foodrecipeapp.utils.base.BaseViewBindingFragment
import com.example.foodrecipeapp.utils.ext.addFragment

@Suppress("TooManyFunctions")
class HomeFragment :
    BaseViewBindingFragment<FragmentHomeBinding>(),
    HomeContract.View,
    OnRecipeItemClickListener,
    OnBackPressedListener {

    private lateinit var dialog: ProgressDialog
    private lateinit var homePresenter: HomePresenter
    private var listRecipes: MutableList<Recipe> = mutableListOf()
    private var listRecentRecipes: MutableList<Recipe> = mutableListOf()

    private val homeChildAdapter: HomeChildAdapter by lazy {
        HomeChildAdapter(
            resources = resources,
            presenter = homePresenter,
            recipeItemClickListener = this
        )
    }

    override fun createBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initData() {
        homePresenter = HomePresenter(
            RecipeRepo.getInstanceRecipeRemoteRepo(RecipeRemoteDataSource.getInstance())
        )
        homePresenter.setView(this)
        homePresenter.getRecipes()

        dialog = ProgressDialog(context).apply {
            setTitle(getString(R.string.loading))
            show()
        }
    }

    override fun initView() {
        homeChildAdapter.setData(getListHomeChild())
        binding.rcvHomeParent.adapter = homeChildAdapter
    }

    override fun onGetRandomRecipesSuccess(listRecipes: MutableList<Recipe>) {
        this.listRecipes = listRecipes
        homeChildAdapter.setRandomListRecipes(listRecipes)
        dialog.dismiss()
    }

    override fun onGetRandomVietnameseRecipesSuccess(listRecipes: MutableList<Recipe>) {
        this.listRecentRecipes = listRecipes
        homeChildAdapter.setRandomListVietnameseRecipes(listRecipes)
        dialog.dismiss()
    }

    override fun onSearchRecipe(searchValue: String) {
        homePresenter.searchRecipes(searchValue)
    }

    override fun onViewMoreRecipes() {
        val viewMoreRecipesFragment = ViewMoreRecipesFragment.newInstance(listRecipes).apply {
            setOnBackListener(this@HomeFragment)
        }
        addFragment(
            R.id.fragment_home_container,
            viewMoreRecipesFragment,
            true
        )
    }

    override fun onViewMoreRecentRecipes() {
        addFragment(
            R.id.fragment_home_container,
            ViewMoreRecentRecipesFragment.newInstance(listRecentRecipes),
            true
        )
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(context, exception?.message, Toast.LENGTH_SHORT).show()
    }

    override fun onRecipeImageClick(recipe: Recipe) {
        addFragment(R.id.fragment_home_container, RecipeDetailFragment.newInstance(recipe), true)
    }

    override fun onBackPressedWithData(listRecipes: MutableList<Recipe>) {
        homeChildAdapter.setRandomListRecipes(listRecipes)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        homeChildAdapter.notifyDataSetChanged()
    }

    private fun getListHomeChild(): MutableList<HomeChild> {
        return mutableListOf(
            HomeChild(HomeChildAdapter.HOME_SEARCH_VIEW),
            HomeChild(HomeChildAdapter.HOME_POPULAR_CATEGORY_VIEW),
            HomeChild(HomeChildAdapter.HOME_RECENT_RECIPE_VIEW)
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
