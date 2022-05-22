package com.example.myapplication.renderer

import com.example.myapplication.renderer.elements.*
import com.google.gson.annotations.SerializedName


enum class ContentType {
    @SerializedName("paragraph")
    PARAGRAPH,

    @SerializedName("text")
    TEXT,

    @SerializedName("block")
    BLOCK,

    @SerializedName("ol")
    OL,

    @SerializedName("li")
    LI,

    @SerializedName("link")
    LINK;


    fun getContentClass(): Class<out Content> {
        return when (this) {
            PARAGRAPH -> ParagraphContent::class.java
            TEXT -> TextContent::class.java
            BLOCK -> BlockContent::class.java
            OL -> OlContent::class.java
            LI -> LiContent::class.java
            LINK -> LinkContent::class.java
        }
    }
}