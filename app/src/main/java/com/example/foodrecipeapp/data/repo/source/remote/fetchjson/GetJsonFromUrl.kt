package com.example.foodrecipeapp.data.repo.source.remote.fetchjson

import android.os.Handler
import android.os.Looper
import com.example.foodrecipeapp.constant.Constant
import com.example.foodrecipeapp.data.model.Recipe
import com.example.foodrecipeapp.data.repo.FetchDataResult
import com.example.foodrecipeapp.listener.OnResultListener
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class GetJsonFromUrl(
    private val urlString: String,
    private val keyEntity: String,
    private val searchValue: String = ""
) {

    private val executor: Executor = Executors.newSingleThreadExecutor()
    private val handler: Handler = Handler(Looper.getMainLooper())

    fun callApi(listener: OnResultListener<MutableList<Recipe>>) {
        callApiGetRandomRecipes(listener)
        callApiGetRandomVietnameseRecipes(listener)
    }

    private fun callApiGetRandomRecipes(listener: OnResultListener<MutableList<Recipe>>) {
        executor.execute {
            val url =
                "$urlString/random?number=$DEFAULT_RECIPE_NUMBER&tags=vegetarian,appetizer${Constant.BASE_API_KEY}"
            val responseJson = getJsonDataFromUrl(url)
            val data = ParseDataWithJson(FetchDataResult.FETCH_TYPE_RANDOM_RECIPE)
                .parseJsonDataToListRecipes(JSONObject(responseJson), keyEntity)
            handler.post {
                try {
                    data.let {
                        listener.onSuccess(it)
                    }
                } catch (ex: JSONException) {
                    listener.onError(ex)
                }
            }
        }
    }

    private fun callApiGetRandomVietnameseRecipes(listener: OnResultListener<MutableList<Recipe>>) {
        executor.execute {
            val url =
                "$urlString/random?number=$DEFAULT_RECIPE_NUMBER&tags=vietnamese${Constant.BASE_API_KEY}"
            val responseJson = getJsonDataFromUrl(url)
            val data = ParseDataWithJson(FetchDataResult.FETCH_TYPE_RANDOM_VIETNAMESE_RECIPE)
                .parseJsonDataToListRecipes(JSONObject(responseJson), keyEntity)
            handler.post {
                try {
                    data.let {
                        listener.onSuccess(it)
                    }
                } catch (ex: JSONException) {
                    listener.onError(ex)
                }
            }
        }
    }

    fun searchRecipes(listener: OnResultListener<MutableList<Recipe>>) {
        executor.execute {
            val url =
                "$urlString/random?number=$DEFAULT_RECIPE_NUMBER&tags=$searchValue${Constant.BASE_API_KEY}"
            val responseJson = getJsonDataFromUrl(url)
            val data = ParseDataWithJson(FetchDataResult.FETCH_TYPE_RANDOM_RECIPE)
                .parseJsonDataToListRecipes(JSONObject(responseJson), keyEntity)
            handler.post {
                try {
                    data.let {
                        listener.onSuccess(it)
                    }
                } catch (ex: JSONException) {
                    listener.onError(ex)
                }
            }
        }
    }

    private fun getJsonDataFromUrl(urlString: String): String {
        val url = URL(urlString)

        val httpURLConnection = url.openConnection() as? HttpURLConnection
        httpURLConnection?.run {
            setRequestProperty(REQUEST_CONTENT_TYPE_PROPERTY, REQUEST_JSON_VALUE)
            setRequestProperty(REQUEST_ACCEPT_PROPERTY, REQUEST_JSON_VALUE)
            requestMethod = METHOD_GET
            connectTimeout = TIME_OUT
            readTimeout = TIME_OUT
            doOutput = true
            connect()
        }

        val bufferedReader = BufferedReader(InputStreamReader(url.openStream()))
        val responseData = StringBuilder()

        bufferedReader.forEachLine {
            responseData.append(it.trim())
        }

        bufferedReader.close()
        httpURLConnection?.disconnect()

        return responseData.toString()
    }

    companion object {
        private const val TIME_OUT = 15000
        private const val METHOD_GET = "GET"
        private const val REQUEST_CONTENT_TYPE_PROPERTY = "Content-Type"
        private const val REQUEST_ACCEPT_PROPERTY = "Accept"
        private const val REQUEST_JSON_VALUE = "application/json"
        private const val DEFAULT_RECIPE_NUMBER = 20
    }
}
