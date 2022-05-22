package com.example.myapplication.renderer.elements

import android.view.ViewGroup
import androidx.core.view.children
import com.example.myapplication.renderer.ContentType

abstract class ContainerContent : Content() {

    override fun inflate(parent: ViewGroup, parentType: ContentType?): ViewGroup {
        return super.inflate(parent, parentType).children.last() as ViewGroup
    }
}