package com.example.myapplication.renderer.elements

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import com.example.myapplication.R
import com.example.myapplication.renderer.ContentType
import com.google.gson.annotations.SerializedName
import org.w3c.dom.Text

class LinkContent(
    @SerializedName("url")
    val url: String
) : Content() {

    override val layoutId: Int
        get() = R.layout.layout_text_html

    override fun inflate(parent: ViewGroup, parentType: ContentType?): ViewGroup {
        return super.inflate(parent, parentType).apply {
            children.lastOrNull()?.let { view ->
                view as TextView

                val textContentInner = this@LinkContent.children?.firstOrNull() as TextContent
                val linkText = textContentInner.text

                val span = SpannableString(textContentInner.text)
                view.movementMethod = LinkMovementMethod.getInstance()

                span.setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        //intents.onNext(DiscountView.Intent.OpenUrl(url))
                        Toast.makeText(widget.context, url, Toast.LENGTH_LONG).show()
                    }
                }, 0, linkText.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
                span.setSpan(
                    ForegroundColorSpan(Color.BLUE),
                    0, linkText.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE
                )
                span.setSpan(
                    object : UnderlineSpan() {
                        override fun updateDrawState(tp: TextPaint) {
                            tp.isUnderlineText = false
                        }
                    },
                    0, linkText.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE
                )

                view.text = span
            }
        }
    }

    override fun renderChildren(parent: ViewGroup, children: List<Content>?) {
        super.renderChildren(parent, null) // Ignore children
    }
}