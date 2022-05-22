package com.suraj.nasagallery.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ImagesFromJson(val list: List<ImageModel>)

@JsonClass(generateAdapter = true)
data class ImageModel(
    @Json(name = "copyright") val copyright: String = "",
    @Json(name = "date") val date: String = "",
    @Json(name = "explanation") val explanation: String = "",
    @Json(name = "hdurl") val hdUrl: String = "",
    @Json(name = "media_type") val mediaType: String = "",
    @Json(name = "title") val title: String = "",
    @Json(name = "url") val url: String = ""
)