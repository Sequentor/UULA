package ru.sequentor.uula.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken

inline fun <reified T> parseJSON(item: JsonElement): T {
    return Gson().fromJson(
        item,
        object : TypeToken<T>() {}.type
    )
}