package com.example.foodrecipeapp.screen.detail

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.adapter.RecipeDetailPagerAdapter
import com.example.foodrecipeapp.data.model.Recipe
import com.example.foodrecipeapp.data.repo.RecipeRepo
import com.example.foodrecipeapp.data.repo.source.remote.RecipeRemoteDataSource
import com.example.foodrecipeapp.databinding.FragmentRecipeDetailBinding
import com.example.foodrecipeapp.databinding.ItemFavouriteRecipeBinding
import com.example.foodrecipeapp.screen.detail.instruction.InstructionFragment
import com.example.foodrecipeapp.screen.detail.overview.OverviewFragment
import com.example.foodrecipeapp.utils.DataLocalManager
import com.example.foodrecipeapp.utils.base.BaseViewBindingFragment
import com.example.foodrecipeapp.utils.ext.goBackFragment
import com.example.foodrecipeapp.utils.ext.loadImageWithUrl
import java.lang.Exception

class RecipeDetailFragment(
    private val recipe: Recipe
) : BaseViewBindingFragment<FragmentRecipeDetailBinding>(), RecipeDetailContract.View {

    private lateinit var recipeDetailPresenter: RecipeDetailPresenter

    private val pagerAdapter: RecipeDetailPagerAdapter by lazy {
        RecipeDetailPagerAdapter(this, getFragmentList())
    }

    override fun createBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRecipeDetailBinding {
        return FragmentRecipeDetailBinding.inflate(inflater, container, false)
    }

    override fun initData() {
        recipeDetailPresenter = RecipeDetailPresenter(
            RecipeRepo.getInstanceRecipeRemoteRepo(RecipeRemoteDataSource.getInstance())
        )
        recipeDetailPresenter.setView(this)
    }

    override fun initView() {
        binding.viewPagerRecipeDetail.adapter = pagerAdapter
        binding.viewPagerRecipeDetail.isUserInputEnabled = false

        binding.imgBackButton.setOnClickListener {
            goBackFragment()
        }

        binding.btnOverview.setOnClickListener {
            handleClickOverviewBtn(binding)
        }

        binding.btnInstruction.setOnClickListener {
            handleClickInstructionBtn(binding)
        }

        showRecipeDetailInfo(recipe)
    }

    @SuppressLint("SetTextI18n")
    override fun showRecipeDetailInfo(recipe: Recipe) {
        binding.tvTitle.text = recipe.title

        if (recipe.servings <= 1) {
            binding.tvServing.text = "${recipe.servings} Serving"
        } else {
            binding.tvServing.text = "${recipe.servings} Servings"
        }

        if (recipe.aggregateLikes <= 1) {
            binding.tvTotalLike.text = "${recipe.aggregateLikes} Like"
        } else {
            binding.tvTotalLike.text = "${recipe.aggregateLikes} Likes"
        }

        if (recipe.readyInMinutes <= 1) {
            binding.tvEstimateTime.text = "${recipe.readyInMinutes} Min"
        } else {
            binding.tvEstimateTime.text = "${recipe.readyInMinutes} Mins"
        }

        val isFavourite =
            DataLocalManager.favouriteRecipesLiveData.value?.contains(recipe) == true
        if (isFavourite) {
            binding.btnFavourite.setIconTintResource(R.color.colorAccent)
        } else {
            binding.btnFavourite.setIconTintResource(R.color.black)
        }

        binding.imgFood.loadImageWithUrl(recipe.image)

        binding.btnFavourite.setOnClickListener {
            handleClickFavouriteButton(recipe)
        }
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(context, exception?.message, Toast.LENGTH_SHORT).show()
    }

    private fun getFragmentList(): List<Fragment> {
        return listOf(
            OverviewFragment.newInstance(recipe),
            InstructionFragment.newInstance(recipe)
        )
    }

    private fun handleClickOverviewBtn(binding: FragmentRecipeDetailBinding) {
        binding.viewPagerRecipeDetail.currentItem = 0

        binding.btnOverview.apply {
            setTextColor(resources.getColor(R.color.white))
            setBackgroundColor(resources.getColor(R.color.colorAccent))
            typeface = Typeface.DEFAULT_BOLD
        }

        binding.btnInstruction.apply {
            setTextColor(resources.getColor(R.color.colorAccent))
            setBackgroundColor(resources.getColor(R.color.white))
            typeface = Typeface.DEFAULT
        }
    }

    private fun handleClickInstructionBtn(binding: FragmentRecipeDetailBinding) {
        binding.viewPagerRecipeDetail.currentItem = 1

        binding.btnInstruction.apply {
            setTextColor(resources.getColor(R.color.white))
            setBackgroundColor(resources.getColor(R.color.colorAccent))
            typeface = Typeface.DEFAULT_BOLD
        }

        binding.btnOverview.apply {
            setTextColor(resources.getColor(R.color.colorAccent))
            setBackgroundColor(resources.getColor(R.color.white))
            typeface = Typeface.DEFAULT
        }
    }

    private fun handleClickFavouriteButton(recipe: Recipe) {
        recipe.isFavourite = !recipe.isFavourite

        if (recipe.isFavourite) {
            binding.btnFavourite.setIconTintResource(R.color.colorAccent)
            Toast.makeText(binding.root.context, R.string.bookmark_recipe, Toast.LENGTH_SHORT)
                .show()
            DataLocalManager.addFavouriteRecipe(recipe)
        } else {
            binding.btnFavourite.setIconTintResource(R.color.black)
            Toast.makeText(binding.root.context, R.string.unmark_recipe, Toast.LENGTH_SHORT)
                .show()
            DataLocalManager.removeFavouriteRecipe(recipe)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(recipe: Recipe) = RecipeDetailFragment(recipe)
    }
}
