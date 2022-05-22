package com.example.myapplication.renderer

import com.example.myapplication.renderer.elements.Content
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class ContentParser {

    fun fromJson(json: String): List<Content> {
        val listType = object : TypeToken<ArrayList<Content>>() {}.type
        return GsonBuilder()
            .registerTypeAdapter(Content::class.java, ContentTypeDeserializer())
            .create()
            .fromJson(json, listType)
    }

    private inner class ContentTypeDeserializer : JsonDeserializer<Content> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): Content {
            val type = json?.asJsonObject?.get("type")?.asString ?: "text"

            return context?.deserialize<Content>(
                json,
                ContentType.valueOf(type.uppercase()).getContentClass()
            ) ?: throw JsonParseException("Failed parse Content ${json?.toString()}")
        }
    }

}