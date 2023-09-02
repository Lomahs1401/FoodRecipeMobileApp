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
import com.example.foodrecipeapp.data.model.Metric
import com.example.foodrecipeapp.data.model.MetricEntry
import com.example.foodrecipeapp.data.model.Recipe
import com.example.foodrecipeapp.data.model.RecipeEntry
import com.example.foodrecipeapp.data.model.Step
import com.example.foodrecipeapp.data.model.StepEntry
import com.example.foodrecipeapp.data.model.Us
import com.example.foodrecipeapp.data.model.UsEntry
import org.json.JSONArray
import org.json.JSONObject

class ParseJsonToObject {
    fun parseJsonToRecipeObject(jsonObject: JSONObject): Recipe {
        val recipe = Recipe()

        jsonObject.let {
            recipe.apply {
                vegetarian = it.optBoolean(RecipeEntry.VEGETARIAN_KEY)
                vegan = it.optBoolean(RecipeEntry.VEGAN_KEY)
                glutenFree = it.optBoolean(RecipeEntry.GLUTEN_FREE_KEY)
                dairyFree = it.optBoolean(RecipeEntry.DAIRY_FREE_KEY)
                veryHealthy = it.optBoolean(RecipeEntry.VERY_HEALTHY_KEY)
                cheap = it.optBoolean(RecipeEntry.CHEAP_KEY)
                veryPopular = it.optBoolean(RecipeEntry.VERY_POPULAR_KEY)
                sustainable = it.optBoolean(RecipeEntry.SUSTAINABLE_KEY)
                weightWatcherSmartPoints = it.optInt(RecipeEntry.WEIGHT_WATCHER_SMART_POINTS_KEY)
                gaps = it.optString(RecipeEntry.GAPS_KEY)
                lowFodmap = it.optBoolean(RecipeEntry.LOW_FOD_MAP_KEY)
                preparationMinutes = it.optInt(RecipeEntry.PREPARATION_MINUTES_KEY)
                cookingMinutes = it.optInt(RecipeEntry.COOKING_MINUTES_KEY)
                aggregateLikes = it.optInt(RecipeEntry.AGGREGATE_LIKES_KEY)
                healthScore = it.optDouble(RecipeEntry.HEALTH_SCORE_KEY)
                creditsText = it.optString(RecipeEntry.CREDITS_TEXT_KEY)
                sourceName = it.optString(RecipeEntry.SOURCE_NAME_KEY)
                pricePerServing = it.optDouble(RecipeEntry.PRICE_PER_SERVING_KEY)

                // Handle extended ingredients array
                extendedIngredients =
                    parseExtendedIngredients(it.getJSONArray(RecipeEntry.EXTENDED_INGREDIENTS_KEY))

                id = it.optInt(RecipeEntry.ID_KEY)
                title = it.optString(RecipeEntry.TITLE_KEY)
                readyInMinutes = it.optInt(RecipeEntry.READY_IN_MINUTES_KEY)
                servings = it.optInt(RecipeEntry.SERVINGS_KEY)
                sourceUrl = it.optString(RecipeEntry.SOURCE_URL_KEY)
                image = it.optString(RecipeEntry.IMAGE_KEY)
                imageType = it.optString(RecipeEntry.IMAGE_TYPE_KEY)
                summary = it.optString(RecipeEntry.SUMMARY_KEY)

                // Handle cuisines array
                cuisines = parseStringArray(it.getJSONArray(RecipeEntry.CUISINES_KEY))

                // Handle dish types array
                dishTypes = parseStringArray(it.getJSONArray(RecipeEntry.DISH_TYPES_KEY))

                // Handle diets array
                diets = parseStringArray(it.getJSONArray(RecipeEntry.DIETS_KEY))

                // Handle occasions array
                occasions = parseStringArray(it.getJSONArray(RecipeEntry.OCCASIONS_KEY))

                instructions = it.optString(RecipeEntry.INSTRUCTIONS_KEY)

                analyzedInstructions =
                    parseAnalyzedInstructions(it.getJSONArray(RecipeEntry.ANALYZED_INSTRUCTIONS_KEY))
                spoonacularSourceUrl = it.optString(RecipeEntry.SPOONACULAR_SOURCE_URL_KEY)
            }
        }

        return recipe
    }

    private fun parseExtendedIngredients(jsonArray: JSONArray): MutableList<ExtendedIngredient> {
        val extendedIngredients = mutableListOf<ExtendedIngredient>()

        // Handle extendedIngredients array
        for (i in 0 until jsonArray.length()) {
            val extendedIngredientJsonObject = jsonArray.getJSONObject(i)
            val measure =
                parseMeasure(extendedIngredientJsonObject.getJSONObject(ExtendedIngredientEntry.MEASURES_KEY))
            val meta =
                parseStringArray(extendedIngredientJsonObject.getJSONArray(ExtendedIngredientEntry.META_KEY))

            val extendedIngredient = ExtendedIngredient(
                id = extendedIngredientJsonObject.optInt(ExtendedIngredientEntry.ID_KEY),
                aisle = extendedIngredientJsonObject.optString(ExtendedIngredientEntry.AISLE_KEY),
                image = extendedIngredientJsonObject.optString(ExtendedIngredientEntry.IMAGE_KEY),
                consistency = extendedIngredientJsonObject.optString(ExtendedIngredientEntry.CONSISTENCY_KEY),
                name = extendedIngredientJsonObject.optString(ExtendedIngredientEntry.NAME_KEY),
                nameClean = extendedIngredientJsonObject.optString(ExtendedIngredientEntry.NAME_CLEAN_KEY),
                original = extendedIngredientJsonObject.optString(ExtendedIngredientEntry.ORIGINAL_KEY),
                originalName = extendedIngredientJsonObject.optString(ExtendedIngredientEntry.ORIGINAL_NAME_KEY),
                amount = extendedIngredientJsonObject.getDouble(ExtendedIngredientEntry.AMOUNT_KEY),
                unit = extendedIngredientJsonObject.optString(ExtendedIngredientEntry.UNIT_KEY),
                meta = meta,
                measure = measure
            )

            extendedIngredients.add(extendedIngredient)
        }

        return extendedIngredients
    }

    private fun parseMeasure(measureObject: JSONObject): Measure {
        val usObject = measureObject.getJSONObject(MeasureEntry.US_KEY)
        val metricObject = measureObject.getJSONObject(MeasureEntry.METRIC_KEY)

        return Measure(
            us = Us(
                amount = usObject.optDouble(UsEntry.AMOUNT_KEY),
                unitShort = usObject.optString(UsEntry.UNIT_SHORT_KEY),
                unitLong = usObject.optString(UsEntry.UNIT_LONG_KEY)
            ),
            metric = Metric(
                amount = metricObject.optDouble(MetricEntry.AMOUNT_KEY),
                unitShort = metricObject.optString(MetricEntry.UNIT_SHORT_KEY),
                unitLong = metricObject.optString(MetricEntry.UNIT_LONG_KEY)
            )
        )
    }

    private fun parseStringArray(jsonArray: JSONArray): MutableList<String> {
        val list = mutableListOf<String>()

        for (i in 0 until jsonArray.length()) {
            list.add(jsonArray.getString(i))
        }

        return list
    }

    private fun parseAnalyzedInstructions(jsonArray: JSONArray): MutableList<AnalyzedInstruction> {
        val analyzedInstructions = mutableListOf<AnalyzedInstruction>()

        for (i in 0 until jsonArray.length()) {
            val analyzedInstructionJsonObject = jsonArray.getJSONObject(i)
            val name = analyzedInstructionJsonObject.optString(AnalyzedInstructionEntry.NAME_KEY)
            val stepsJsonArray =
                analyzedInstructionJsonObject.getJSONArray(AnalyzedInstructionEntry.STEP_KEY)
            val steps = parseSteps(stepsJsonArray)

            val analyzedInstruction = AnalyzedInstruction(
                name = name,
                steps = steps
            )

            analyzedInstructions.add(analyzedInstruction)
        }

        return analyzedInstructions
    }

    private fun parseSteps(jsonArray: JSONArray): MutableList<Step> {
        val steps = mutableListOf<Step>()

        for (i in 0 until jsonArray.length()) {
            val stepJsonObject = jsonArray.getJSONObject(i)
            val ingredientsJsonArray = stepJsonObject.getJSONArray(StepEntry.INGREDIENTS_KEY)
            val equipmentsJsonArray = stepJsonObject.getJSONArray(StepEntry.EQUIPMENTS_KEY)

            val ingredients = parseIngredients(ingredientsJsonArray)
            val equipments = parseEquipments(equipmentsJsonArray)

            val step = Step(
                number = stepJsonObject.optInt(StepEntry.NUMBER_KEY),
                step = stepJsonObject.optString(StepEntry.STEP_KEY),
                ingredients = ingredients,
                equipments = equipments,
            )

            steps.add(step)
        }

        return steps
    }

    private fun parseIngredients(jsonArray: JSONArray): MutableList<Ingredient> {
        val ingredients = mutableListOf<Ingredient>()

        for (i in 0 until jsonArray.length()) {
            val ingredientJsonObject = jsonArray.getJSONObject(i)
            val ingredient = Ingredient(
                id = ingredientJsonObject.optInt(IngredientEntry.ID_KEY),
                name = ingredientJsonObject.optString(IngredientEntry.NAME_KEY),
                localizedName = ingredientJsonObject.optString(IngredientEntry.LOCALIZED_NAME_KEY),
                image = ingredientJsonObject.optString(IngredientEntry.IMAGE_KEY)
            )
            ingredients.add(ingredient)
        }

        return ingredients
    }

    private fun parseEquipments(jsonArray: JSONArray): MutableList<Equipment> {
        val equipments = mutableListOf<Equipment>()

        for (i in 0 until jsonArray.length()) {
            val equipmentJsonObject = jsonArray.getJSONObject(i)
            val equipment = Equipment(
                id = equipmentJsonObject.optInt(EquipmentEntry.ID_KEY),
                name = equipmentJsonObject.optString(EquipmentEntry.NAME_KEY),
                localizedName = equipmentJsonObject.optString(EquipmentEntry.LOCALIZED_NAME_KEY),
                image = equipmentJsonObject.optString(EquipmentEntry.IMAGE_KEY)
            )
            equipments.add(equipment)
        }

        return equipments
    }
}
