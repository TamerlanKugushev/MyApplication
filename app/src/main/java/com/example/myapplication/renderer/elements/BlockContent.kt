package com.example.myapplication.renderer.elements

import android.text.SpannableString
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.example.myapplication.R
import com.example.myapplication.renderer.ContentType
import com.google.gson.annotations.SerializedName

open class BlockContent(
    @SerializedName("title")
    private val title: String = "",
    @SerializedName("isOpen")
    private val isOpen: Boolean = true
) : ContainerContent() {

    companion object {
        const val COLLAPSED: String = "collapsed"
        const val EXPANDED: String = "expanded"
        const val BLOCK_TITLE_PREFIX: String = "block_title_"
    }

    override val layoutId
        get() = R.layout.layout_block_title

    override fun inflate(parent: ViewGroup, parentType: ContentType?): ViewGroup {
        val block = super.inflate(parent, parentType)

        val titleView = block.children.first() as TextView
        val blockContent = block.children.last() as ViewGroup

        titleView.apply {
            text = SpannableString(title)
            blockExpand(isOpen, this, blockContent)

            setOnClickListener { expandedView ->
                val isExpanded = expandedView.tag == "block_title_expanded"
                blockExpand(!isExpanded, expandedView as TextView, blockContent)
            }
        }

        return blockContent
    }

    private fun blockExpand(isExpanded: Boolean, title: TextView, block: ViewGroup) {
        val postfix = if (!isExpanded) COLLAPSED else EXPANDED

        title.apply {
            tag = "$BLOCK_TITLE_PREFIX$postfix"
            setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                ResourcesCompat.getDrawable(
                    resources,
                    if (isExpanded) R.drawable.ic_box else R.drawable.ic_box1,
                    context.theme
                ),
                null
            )
        }

        block.visibility = if (isExpanded) View.VISIBLE else View.GONE
    }
}