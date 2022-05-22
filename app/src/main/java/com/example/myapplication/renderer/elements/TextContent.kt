package com.example.myapplication.renderer.elements

import android.text.SpannableString
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import com.example.myapplication.R
import com.example.myapplication.renderer.ContentType
import com.google.gson.annotations.SerializedName

class TextContent(
    @SerializedName("text")
    val text: String
) : Content() {

    override val layoutId: Int
        get() = R.layout.layout_text_html

    override fun inflate(parent: ViewGroup, parentType: ContentType?): ViewGroup {
        return super.inflate(parent, parentType).apply {
            children.lastOrNull()?.let { view ->
                view as TextView
                view.text = SpannableString(this@TextContent.text)
            }
        }
    }
}