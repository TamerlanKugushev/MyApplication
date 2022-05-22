package com.example.myapplication.renderer.elements

import android.view.ViewGroup
import androidx.core.view.children
import com.example.myapplication.R
import com.example.myapplication.renderer.ContentType

class DocContent : ContainerContent() {
    override val layoutId
        get() = R.layout.layout_doc

    override fun inflate(parent: ViewGroup, parentType: ContentType?): ViewGroup {
        return super.inflate(parent, parentType).children.first() as ViewGroup
    }
}