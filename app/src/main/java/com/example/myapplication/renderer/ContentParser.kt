package com.example.myapplication.renderer

import com.example.myapplication.renderer.elements.Content
import com.example.myapplication.renderer.elements.BlockWithKind
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
            val kind = json?.asJsonObject?.get("kind")?.asString

            var typeClass = if (type == "block" && kind != null) {
                BlockWithKind::class.java
            } else {
                ContentType.valueOf(type.uppercase()).getContentClass()
            }

            return context?.deserialize<Content>(
                json,
                typeClass
            ) ?: throw JsonParseException("Failed parse Content ${json?.toString()}")
        }
    }

}