package com.example.foodrecipeapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipeapp.data.model.Equipment
import com.example.foodrecipeapp.data.model.Ingredient
import com.example.foodrecipeapp.data.model.Step
import com.example.foodrecipeapp.databinding.ItemStepBinding

class StepAdapter : RecyclerView.Adapter<StepAdapter.StepViewHolder>() {

    private var listSteps: MutableList<Step> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val binding =
            ItemStepBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StepViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listSteps.size
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        holder.bindData(listSteps[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setStepsData(listSteps: MutableList<Step>) {
        this.listSteps = listSteps
        notifyDataSetChanged()
    }

    inner class StepViewHolder(private val binding: ItemStepBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindData(step: Step) {
            binding.numberStep.text = step.number.toString()
            binding.stepContent.text = step.step

            val listEquipments: MutableList<Equipment> = mutableListOf()
            val listIngredients: MutableList<Ingredient> = mutableListOf()

            val equipmentsLength = step.equipments.size
            val ingredientsLength = step.ingredients.size

            if (equipmentsLength != 0) {
                binding.tvEquipment.visibility = View.VISIBLE
                binding.rcvEquipments.visibility = View.VISIBLE

                for (i in 0 until equipmentsLength) {
                    listEquipments.add(step.equipments[i])
                }

                val equipmentAdapter = EquipmentAdapter()
                equipmentAdapter.setData(listEquipments)
                binding.rcvEquipments.adapter = equipmentAdapter
            } else {
                binding.tvEquipment.visibility = View.GONE
                binding.rcvEquipments.visibility = View.GONE
            }

            if (ingredientsLength != 0) {
                binding.tvIngredient.visibility = View.VISIBLE
                binding.rcvIngredients.visibility = View.VISIBLE

                for (i in 0 until ingredientsLength) {
                    listIngredients.add(step.ingredients[i])
                }

                val ingredientAdapter = IngredientAdapter()
                ingredientAdapter.setData(listIngredients)
                binding.rcvIngredients.adapter = ingredientAdapter
            } else {
                binding.tvIngredient.visibility = View.GONE
                binding.rcvIngredients.visibility = View.GONE
            }
        }
    }
}
