package com.example.myapplication.renderer.elements

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import com.example.myapplication.R

class LiContent : ContainerContent() {

    override val layoutId
        get() = R.layout.layout_block_item_li

    override fun inflate(parent: ViewGroup): ViewGroup {
        return super.inflate(parent).apply {
            children.firstOrNull().let { title ->
                title as TextView
                @SuppressLint("SetTextI18n")
                title.text = parent.childCount.toString()
            }
        }
    }
}