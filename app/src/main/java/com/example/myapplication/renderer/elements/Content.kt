package com.example.myapplication.renderer.elements

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.renderer.ContentType
import com.google.gson.annotations.SerializedName

abstract class Content(
    @SerializedName("type") val type: ContentType = ContentType.TEXT,
    @SerializedName("children")  val children: List<Content>? = null
) {

    abstract val layoutId: Int?

    protected open fun inflate(parent: ViewGroup, parentType: ContentType? = null): ViewGroup {
        return layoutId?.let {  layoutId ->
            LayoutInflater.from(parent.context).inflate(layoutId, parent, true) as ViewGroup
        } ?: parent
    }

    protected open fun renderChildren(parent: ViewGroup, children: List<Content>?) {
        children?.onEach { child ->
            child.render(parent, type)
        }
    }

    fun render(parent: ViewGroup, parentType: ContentType? = null) {
        renderChildren(inflate(parent, parentType), children)
    }
}