package com.example.foodrecipeapp.data.repo.source.remote.fetchjson

import com.example.foodrecipeapp.data.model.AnalyzedInstruction
import com.example.foodrecipeapp.data.model.AnalyzedInstructionEntry
import com.example.foodrecipeapp.data.model.Equipment
import com.example.foodrecipeapp.data.model.EquipmentEntry
import com.example.foodrecipeapp.data.model.ExtendedIngredient
import com.example.foodrecipeapp.data.model.ExtendedIngredientEntry
import com.example.foodrecipeapp.data.model.Ingredient
import com.example.foodrecipeapp.data.model.IngredientEntry
import com.example.foodrecipeapp.data.model.Measure
import com.example.foodrecipeapp.data.model.MeasureEntry
import com.example.foodrecipeapp.data.model.MetricEntry
import com.example.foodrecipeapp.data.model.Recipe
import com.example.foodrecipeapp.data.model.RecipeEntry
import com.example.foodrecipeapp.data.model.Step
import com.example.foodrecipeapp.data.model.StepEntry
import com.example.foodrecipeapp.data.model.UsEntry
import org.json.JSONArray
import org.json.JSONObject

class ParseObjectToJson {
    fun parseObjectToJsonObject(recipe: Recipe): JSONObject {
        val jsonObject = JSONObject().apply {
            put(RecipeEntry.VEGETARIAN_KEY, recipe.vegetarian)
            put(RecipeEntry.VEGAN_KEY, recipe.vegan)
            put(RecipeEntry.GLUTEN_FREE_KEY, recipe.glutenFree)
            put(RecipeEntry.DAIRY_FREE_KEY, recipe.dairyFree)
            put(RecipeEntry.VERY_HEALTHY_KEY, recipe.veryHealthy)
            put(RecipeEntry.CHEAP_KEY, recipe.cheap)
            put(RecipeEntry.VERY_POPULAR_KEY, recipe.veryPopular)
            put(RecipeEntry.SUSTAINABLE_KEY, recipe.sustainable)
            put(RecipeEntry.WEIGHT_WATCHER_SMART_POINTS_KEY, recipe.weightWatcherSmartPoints)
            put(RecipeEntry.GAPS_KEY, recipe.gaps)
            put(RecipeEntry.LOW_FOD_MAP_KEY, recipe.lowFodmap)
            put(RecipeEntry.PREPARATION_MINUTES_KEY, recipe.preparationMinutes)
            put(RecipeEntry.COOKING_MINUTES_KEY, recipe.cookingMinutes)
            put(RecipeEntry.AGGREGATE_LIKES_KEY, recipe.aggregateLikes)
            put(RecipeEntry.HEALTH_SCORE_KEY, recipe.healthScore)
            put(RecipeEntry.CREDITS_TEXT_KEY, recipe.creditsText)

            put(
                RecipeEntry.EXTENDED_INGREDIENTS_KEY,
                extendedIngredientsArrayToJsonArray(recipe.extendedIngredients)
            )

            put(RecipeEntry.ID_KEY, recipe.id)
            put(RecipeEntry.TITLE_KEY, recipe.title)
            put(RecipeEntry.READY_IN_MINUTES_KEY, recipe.readyInMinutes)
            put(RecipeEntry.SERVINGS_KEY, recipe.servings)
            put(RecipeEntry.SOURCE_URL_KEY, recipe.sourceUrl)
            put(RecipeEntry.IMAGE_KEY, recipe.image)
            put(RecipeEntry.IMAGE_TYPE_KEY, recipe.imageType)
            put(RecipeEntry.SUMMARY_KEY, recipe.summary)

            put(RecipeEntry.CUISINES_KEY, stringListToJsonArray(recipe.cuisines))
            put(RecipeEntry.DISH_TYPES_KEY, stringListToJsonArray(recipe.dishTypes))
            put(RecipeEntry.DIETS_KEY, stringListToJsonArray(recipe.diets))
            put(RecipeEntry.OCCASIONS_KEY, stringListToJsonArray(recipe.occasions))

            put(RecipeEntry.INSTRUCTIONS_KEY, recipe.instructions)
            put(
                RecipeEntry.ANALYZED_INSTRUCTIONS_KEY,
                analyzedInstructionsArrayToJsonArray(recipe.analyzedInstructions)
            )
            put(RecipeEntry.SPOONACULAR_SOURCE_URL_KEY, recipe.spoonacularSourceUrl)
        }

        return jsonObject
    }

    private fun extendedIngredientsArrayToJsonArray(extendedIngredients: MutableList<ExtendedIngredient>): JSONArray {
        val jsonArray = JSONArray()

        for (extendedIngredient in extendedIngredients) {
            val extendedIngredientObject = JSONObject()

            val metaJsonArray = stringListToJsonArray(extendedIngredient.meta)
            val measuresJsonObject = measureObjectToJsonObject(extendedIngredient.measure)

            extendedIngredientObject.put(ExtendedIngredientEntry.ID_KEY, extendedIngredient.id)
            extendedIngredientObject.put(
                ExtendedIngredientEntry.AISLE_KEY,
                extendedIngredient.aisle
            )
            extendedIngredientObject.put(
                ExtendedIngredientEntry.IMAGE_KEY,
                extendedIngredient.image
            )
            extendedIngredientObject.put(
                ExtendedIngredientEntry.CONSISTENCY_KEY,
                extendedIngredient.consistency
            )
            extendedIngredientObject.put(ExtendedIngredientEntry.NAME_KEY, extendedIngredient.name)
            extendedIngredientObject.put(
                ExtendedIngredientEntry.NAME_CLEAN_KEY,
                extendedIngredient.nameClean
            )
            extendedIngredientObject.put(
                ExtendedIngredientEntry.ORIGINAL_KEY,
                extendedIngredient.original
            )
            extendedIngredientObject.put(
                ExtendedIngredientEntry.ORIGINAL_NAME_KEY,
                extendedIngredient.originalName
            )
            extendedIngredientObject.put(
                ExtendedIngredientEntry.AMOUNT_KEY,
                extendedIngredient.amount
            )
            extendedIngredientObject.put(ExtendedIngredientEntry.UNIT_KEY, extendedIngredient.unit)
            extendedIngredientObject.put(ExtendedIngredientEntry.META_KEY, metaJsonArray)
            extendedIngredientObject.put(ExtendedIngredientEntry.MEASURES_KEY, measuresJsonObject)

            jsonArray.put(extendedIngredientObject)
        }

        return jsonArray
    }

    private fun stringListToJsonArray(stringList: MutableList<String>): JSONArray {
        val jsonArray = JSONArray()
        for (item in stringList) {
            jsonArray.put(item)
        }
        return jsonArray
    }

    private fun measureObjectToJsonObject(measure: Measure): JSONObject {
        val jsonObject = JSONObject()

        val usJsonObject = JSONObject()
        usJsonObject.put(UsEntry.AMOUNT_KEY, measure.us.amount)
        usJsonObject.put(UsEntry.UNIT_SHORT_KEY, measure.us.unitShort)
        usJsonObject.put(UsEntry.UNIT_LONG_KEY, measure.us.unitLong)

        val metricJsonObject = JSONObject()
        metricJsonObject.put(MetricEntry.AMOUNT_KEY, measure.metric.amount)
        metricJsonObject.put(MetricEntry.UNIT_SHORT_KEY, measure.metric.unitShort)
        metricJsonObject.put(MetricEntry.UNIT_LONG_KEY, measure.metric.unitLong)

        jsonObject.put(MeasureEntry.US_KEY, usJsonObject)
        jsonObject.put(MeasureEntry.METRIC_KEY, metricJsonObject)

        return jsonObject
    }

    private fun analyzedInstructionsArrayToJsonArray(
        analyzedInstructions: MutableList<AnalyzedInstruction>
    ): JSONArray {
        val jsonArray = JSONArray()

        for (analyzedInstruction in analyzedInstructions) {
            val analyzedInstructionJsonObject = JSONObject()

            val stepJsonArray = stepsArrayToJsonArray(analyzedInstruction.steps)

            analyzedInstructionJsonObject.put(
                AnalyzedInstructionEntry.NAME_KEY,
                analyzedInstruction.name
            )
            analyzedInstructionJsonObject.put(AnalyzedInstructionEntry.STEP_KEY, stepJsonArray)
        }

        return jsonArray
    }

    private fun stepsArrayToJsonArray(steps: MutableList<Step>): JSONArray {
        val jsonArray = JSONArray()

        for (step in steps) {
            val stepJsonObject = JSONObject()

            val ingredientJsonArray = ingredientsArrayToJsonArray(step.ingredients)
            val equipmentsJsonArray = equipmentsArrayToJsonArray(step.equipments)

            stepJsonObject.put(StepEntry.NUMBER_KEY, step.number)
            stepJsonObject.put(StepEntry.STEP_KEY, step.step)
            stepJsonObject.put(StepEntry.INGREDIENTS_KEY, ingredientJsonArray)
            stepJsonObject.put(StepEntry.EQUIPMENTS_KEY, equipmentsJsonArray)
            stepJsonObject.put(StepEntry.EQUIPMENTS_KEY, equipmentsJsonArray)

            jsonArray.put(stepJsonObject)
        }

        return jsonArray
    }

    private fun ingredientsArrayToJsonArray(ingredients: MutableList<Ingredient>): JSONArray {
        val jsonArray = JSONArray()

        for (ingredient in ingredients) {
            val ingredientJsonObject = JSONObject()

            ingredientJsonObject.put(IngredientEntry.ID_KEY, ingredient.id)
            ingredientJsonObject.put(IngredientEntry.NAME_KEY, ingredient.name)
            ingredientJsonObject.put(IngredientEntry.LOCALIZED_NAME_KEY, ingredient.localizedName)
            ingredientJsonObject.put(IngredientEntry.IMAGE_KEY, ingredient.image)

            jsonArray.put(ingredientJsonObject)
        }

        return jsonArray
    }

    private fun equipmentsArrayToJsonArray(equipments: MutableList<Equipment>): JSONArray {
        val jsonArray = JSONArray()

        for (equipment in equipments) {
            val equipmentJsonObject = JSONObject()

            equipmentJsonObject.put(EquipmentEntry.ID_KEY, equipment.id)
            equipmentJsonObject.put(EquipmentEntry.NAME_KEY, equipment.name)
            equipmentJsonObject.put(EquipmentEntry.LOCALIZED_NAME_KEY, equipment.localizedName)
            equipmentJsonObject.put(EquipmentEntry.IMAGE_KEY, equipment.image)

            jsonArray.put(equipmentJsonObject)
        }

        return jsonArray
    }
}
