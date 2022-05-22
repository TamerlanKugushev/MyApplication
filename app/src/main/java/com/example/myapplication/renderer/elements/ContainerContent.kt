package com.example.myapplication.renderer.elements

import android.view.ViewGroup
import androidx.core.view.children

abstract class ContainerContent : Content() {

    override fun inflate(parent: ViewGroup): ViewGroup {
        return super.inflate(parent).children.last() as ViewGroup
    }
}