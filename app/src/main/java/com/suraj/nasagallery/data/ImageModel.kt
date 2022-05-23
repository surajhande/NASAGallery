package com.suraj.nasagallery.data

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class ImageModel(
    @Json(name = "copyright") val copyright: String = "",
    @Json(name = "date") val date: String = "",
    @Json(name = "explanation") val explanation: String = "",
    @Json(name = "hdurl") val hdUrl: String = "",
    @Json(name = "media_type") val mediaType: String = "",
    @Json(name = "title") val title: String = "",
    @Json(name = "url") val url: String = ""
) : Parcelable