package com.suraj.nasagallery.data

import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Before
import org.junit.Test

class ImageModelTest {

    lateinit var moshi: Moshi

    companion object {
        const val rawJson = """ 
        {
            "copyright": "Steve Mazlin",
            "date": "2019-12-03",
            "explanation": "Short sample explaination.",
            "hdurl": "https://apod.nasa.gov/apod/image/1912/M27_Mazlin_2000.jpg",
            "media_type": "image",
            "service_version": "v1",
            "title": "M27: The Dumbbell Nebula",
            "url": "https://apod.nasa.gov/apod/image/1912/M27_Mazlin_960.jpg"
        }
        """
    }
    @Before
    fun setup() {
        moshi = Moshi.Builder().build()
    }

    @Test
    fun `Load JSON into ImageModel class`() {
        val adapter: JsonAdapter<ImageModel> =
            moshi.adapter(ImageModel::class.java)
        val imageModel = adapter.fromJson(rawJson)
        assertThat(imageModel).isNotNull()
        assertThat(imageModel!!.copyright).isEqualTo("Steve Mazlin")
        assertThat(imageModel!!.date).isEqualTo("2019-12-03")
        assertThat(imageModel!!.explanation).isEqualTo("Short sample explaination.")
        assertThat(imageModel!!.hdUrl).isEqualTo("https://apod.nasa.gov/apod/image/1912/M27_Mazlin_2000.jpg")
        assertThat(imageModel!!.mediaType).isEqualTo("image")
        assertThat(imageModel!!.title).isEqualTo("M27: The Dumbbell Nebula")
        assertThat(imageModel!!.url).isEqualTo("https://apod.nasa.gov/apod/image/1912/M27_Mazlin_960.jpg")
    }
}