package ru.sequentor.uula.model

import com.google.gson.JsonObject

data class Lesson(
    val kind: String,
    val item: JsonObject
) : RVData

data class Survey(
    val visited: Boolean,
    val id: Int,
    val title: String,
    val about: String,
    val questions_count: Int
) : RVData

data class OfflineMaterial(
    val visited: Boolean,
    val id: Int,
    val title: String,
    val about: String,
    val format: String,
    val file_extension: String
) : RVData

data class Video(
    val visited: Boolean,
    val id: Int,
    val title: String,
    val description: String,
    val images: Images,
    val about: String,
    val duration: Int,
    val comments_count: Int
) : RVData

data class Images(
    val large: String,
    val small: String,
    val medium: String,
    val xlarge: String,
    val xsmall: String,
    val xxlarge: String
)

data class LessonsProgress(
    var current: Int = 0,
    var max: Int = 0
)

interface RVData