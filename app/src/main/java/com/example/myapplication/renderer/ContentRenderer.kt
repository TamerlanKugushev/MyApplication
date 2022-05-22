package com.example.myapplication.renderer

import android.view.ViewGroup
import com.example.myapplication.renderer.elements.Content

class ContentRenderer(private val container: ViewGroup) {

    fun render(contents: List<Content>) {
        contents.onEach { it.render(container) }
    }
}