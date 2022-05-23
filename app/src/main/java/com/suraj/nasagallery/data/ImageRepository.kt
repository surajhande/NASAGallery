package com.suraj.nasagallery.data

import android.content.Context
import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

interface ImageRepository {
    suspend fun loadImageModelsFromJson(): ArrayList<ImageModel>
}

class DefaultImageRepository @Inject constructor(
    private val context: Context,
    private val moshi: Moshi
) : ImageRepository {

    companion object {
        private const val JSON_FILE = "data.json"
    }

    override suspend fun loadImageModelsFromJson(): ArrayList<ImageModel> {
        var imageList: List<ImageModel>?
        withContext(Dispatchers.IO) {
            var jsonData = ""
            Timber.d("SURAJ")
            try {
                val reader = context.assets.open(JSON_FILE).bufferedReader()
                jsonData = reader.readText()
                reader.close()
                Timber.d("Success reading: $jsonData")
            } catch (e: Exception) {
                Timber.d("Error reading json" + e.printStackTrace())
            }
            val adapter: JsonAdapter<List<ImageModel>> =
                moshi.adapter(Types.newParameterizedType(List::class.java, ImageModel::class.java))
            imageList = adapter.fromJson(jsonData)
            println(jsonData)
        }
        return ArrayList(imageList?.toMutableList() ?: arrayListOf())
    }

}