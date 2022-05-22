package com.example.myapplication.renderer.elements

import android.annotation.SuppressLint
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import com.example.myapplication.R
import com.example.myapplication.renderer.ContentType

class LiContent : ContainerContent() {

    override val layoutId
        get() = R.layout.layout_block_item_li

    override fun inflate(parent: ViewGroup, parentType: ContentType?): ViewGroup {
        return super.inflate(parent, parentType).apply {
            children.firstOrNull().let { title ->
                title as TextView
                @SuppressLint("SetTextI18n")

                when(parentType) {
                    ContentType.OL ->  {
                        title.text = parent.childCount.toString()
                        title.setBackgroundResource(R.drawable.number_circle_background)
                    }
                    ContentType.UL ->  {
                        title.text = getBullet()
                    }
                    else -> {
                        // do nothing
                    }
                }
            }
        }
    }

    private fun getBullet(): SpannableString = SpannableString("").apply {
        setSpan(BulletSpan(), 0, 0, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    }
}