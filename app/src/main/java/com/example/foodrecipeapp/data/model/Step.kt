package com.example.foodrecipeapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Step(
    val number: Int = 0,
    val step: String = "",
    val ingredients: MutableList<Ingredient> = mutableListOf(),
    val equipments: MutableList<Equipment> = mutableListOf(),
) : Parcelable

object StepEntry {
    const val NUMBER_KEY = "number"
    const val STEP_KEY = "step"
    const val INGREDIENTS_KEY = "ingredients"
    const val EQUIPMENTS_KEY = "equipment"
    const val LENGTH_KEY = "length"
}
