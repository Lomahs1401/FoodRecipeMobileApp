package com.example.foodrecipeapp.screen.detail.instruction

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import com.example.foodrecipeapp.adapter.StepAdapter
import com.example.foodrecipeapp.data.model.Recipe
import com.example.foodrecipeapp.data.model.Step
import com.example.foodrecipeapp.data.repo.RecipeRepo
import com.example.foodrecipeapp.data.repo.source.remote.RecipeRemoteDataSource
import com.example.foodrecipeapp.databinding.FragmentInstructionBinding
import com.example.foodrecipeapp.utils.base.BaseViewBindingFragment

class InstructionFragment(private val recipe: Recipe) :
    BaseViewBindingFragment<FragmentInstructionBinding>(), InstructionContract.View {

    private lateinit var instructionPresenter: InstructionPresenter
    private val stepAdapter: StepAdapter by lazy {
        StepAdapter()
    }

    override fun createBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentInstructionBinding {
        return FragmentInstructionBinding.inflate(inflater, container, false)
    }

    override fun initData() {
        instructionPresenter = InstructionPresenter(
            RecipeRepo.getInstanceRecipeRemoteRepo(RecipeRemoteDataSource.getInstance())
        )
        instructionPresenter.setView(this)
    }

    override fun initView() {
        showRecipeInstruction(recipe)
    }

    override fun showRecipeInstruction(recipe: Recipe) {
        binding.tvInstruction.text =
            HtmlCompat.fromHtml(recipe.instructions, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.tvInstruction.movementMethod = LinkMovementMethod.getInstance()

        val listSteps: MutableList<Step> = mutableListOf()
        val analyzedInstructionLength = recipe.analyzedInstructions.size

        for (i in 0 until analyzedInstructionLength) {
            val stepsLength = recipe.analyzedInstructions[i].steps.size
            for (j in 0 until  stepsLength) {
                listSteps.add(recipe.analyzedInstructions[i].steps[j])
            }
        }

        stepAdapter.setStepsData(listSteps)
        binding.rcvStep.adapter = stepAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance(recipe: Recipe) = InstructionFragment(recipe)
    }
}
