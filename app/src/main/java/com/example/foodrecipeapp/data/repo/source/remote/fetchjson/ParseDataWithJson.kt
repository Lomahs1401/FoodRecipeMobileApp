package com.example.foodrecipeapp.data.repo.source.remote.fetchjson

import android.util.Log
import com.example.foodrecipeapp.data.model.Recipe
import com.example.foodrecipeapp.data.model.RecipeEntry
import com.example.foodrecipeapp.data.repo.FetchDataResult
import com.example.foodrecipeapp.utils.ext.notNull
import org.json.JSONException
import org.json.JSONObject

class ParseDataWithJson(private val fetchDataType: Int) {
    fun parseJsonDataToListRecipes(
        jsonObject: JSONObject?,
        keyEntity: String
    ): FetchDataResult<MutableList<Recipe>> {
        val data = mutableListOf<Recipe>()
        return try {
            val jsonArray = jsonObject?.getJSONArray(keyEntity)
            for (i in 0 until (jsonArray?.length() ?: 0)) {
                val item = parseJsonDataToRecipeObject(jsonArray?.getJSONObject(i), keyEntity)
                item.notNull {
                    data.add(it)
                }
            }
            FetchDataResult.Success(data, fetchDataType)
        } catch (e: JSONException) {
            FetchDataResult.Error(e)
        }
    }

    private fun parseJsonDataToRecipeObject(jsonObject: JSONObject?, keyEntity: String): Recipe? {
        try {
            jsonObject?.notNull {
                return when (keyEntity) {
                    RecipeEntry.RECIPES_OBJECT -> ParseJsonToObject().parseJsonToRecipeObject(it)
                    else -> null
                }
            }
        } catch (e: JSONException) {
            Log.e("ParseDataWithJson", "Error parsing JSON: $e")
        }
        return null
    }
}
