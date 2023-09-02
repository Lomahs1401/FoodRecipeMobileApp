package com.example.foodrecipeapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipeapp.constant.Constant
import com.example.foodrecipeapp.data.model.Ingredient
import com.example.foodrecipeapp.databinding.ItemIngredientBinding
import com.example.foodrecipeapp.utils.ext.loadImageWithUrl

class IngredientAdapter : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    private var listIngredients: MutableList<Ingredient> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding =
            ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listIngredients.size
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bindData(listIngredients[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listIngredients: MutableList<Ingredient>) {
        this.listIngredients = listIngredients
        notifyDataSetChanged()
    }

    inner class IngredientViewHolder(private val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(ingredient: Ingredient) {
            binding.imgIngredient.loadImageWithUrl(Constant.BASE_URL_IMAGE_INGREDIENT + ingredient.image)
            binding.tvIngredientName.text = ingredient.name
        }
    }
}