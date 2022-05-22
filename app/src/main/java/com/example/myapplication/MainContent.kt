package com.example.myapplication

import com.example.myapplication.renderer.ContentType
import com.google.gson.annotations.SerializedName

data class MainContent(
    var mainContent: ArrayList<Content>?,
)

data class Content(
    @SerializedName("type")
    var type: ContentType = ContentType.TEXT,
    @SerializedName("children")
    var children: ArrayList<Content>?,
    @SerializedName("text")
    var text: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("isOpen")
    var isOpen: Boolean? = null,
    @SerializedName("url")
    var url: String? = null
)