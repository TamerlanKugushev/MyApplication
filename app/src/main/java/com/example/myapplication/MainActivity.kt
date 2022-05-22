package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.renderer.ContentParser
import com.example.myapplication.renderer.ContentRenderer
import java.io.BufferedReader


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        renderMainContent()
    }

    private fun renderMainContent() {
        val json = resources.openRawResource(intent.getIntExtra("RESOURCE", 0))
            .bufferedReader()
            .use(BufferedReader::readText)


        val content = ContentParser().fromJson(json)
        ContentRenderer(binding.container).render(content)
    }

//    private fun render(container: ViewGroup, content: Content) {
//        val layoutInflater = LayoutInflater.from(this)
//        var innerContainer = container
//        when (content.type) {
//            ContentType.PARAGRAPH -> {
//                val viewGroup =
//                    layoutInflater.inflate(R.layout.layout_paragraph, container) as ViewGroup
//                innerContainer = viewGroup.children.last() as ViewGroup
//            }
//
//            ContentType.TEXT -> {
//                if (container.children.lastOrNull() is TextView
//                    && container.children.last().tag == "text"
//                ) {
//                    val textView = container.children.last() as TextView
//                    val spans = (textView.text as SpannableString).getSpans<Any>(
//                        0,
//                        textView.text.length
//                    )
//
//                    val span = SpannableStringBuilder().append(textView.text.toString())
//                        .append(content.text)
//
//                    spans.onEach {
//                        span.setSpan(it, 0, textView.text.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
//                    }
//
//                    textView.text = span
//                } else {
//                    val viewGroup =
//                        layoutInflater.inflate(R.layout.layout_text_html, container) as ViewGroup
//                    viewGroup.children.last().apply {
//                        this as TextView
//                        text = SpannableString(content.text)
//                    }
//                }
//            }
//
//            ContentType.LINK -> {
//                val viewGroup =
//                    layoutInflater.inflate(R.layout.layout_text_html, container) as ViewGroup
//                viewGroup.children.last().apply {
//                    this as TextView
//                    val link = content.children?.firstOrNull()?.text
//                    val span = SpannableString(link)
//                    movementMethod = LinkMovementMethod.getInstance()
//                    span.setSpan(object : ClickableSpan() {
//                        override fun onClick(widget: View) {
//                            //intents.onNext(DiscountView.Intent.OpenUrl(content.url))
//                            Toast.makeText(widget.context, "CLICK", Toast.LENGTH_LONG).show()
//                        }
//                    }, 0, link?.length ?: 0, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
//                    span.setSpan(
//                        ForegroundColorSpan(Color.BLUE),
//                        0, link?.length ?: 0, Spanned.SPAN_INCLUSIVE_INCLUSIVE
//                    )
//                    span.setSpan(
//                        object : UnderlineSpan() {
//                            override fun updateDrawState(tp: TextPaint) {
//                                tp.isUnderlineText = false
//                            }
//                        },
//                        0, link?.length ?: 0, Spanned.SPAN_INCLUSIVE_INCLUSIVE
//                    )
//
//                    text = span
//                }
//                return
//            }
//

//
//            ContentType.OL -> {
//                val viewGroup =
//                    layoutInflater.inflate(R.layout.layout_block_item_ol, container) as ViewGroup
//                innerContainer = viewGroup.children.last() as ViewGroup
//            }
//
//            ContentType.LI -> {
//                val viewGroup =
//                    layoutInflater.inflate(R.layout.layout_block_item_li, container) as ViewGroup
//                val li = viewGroup.children.last() as ViewGroup
//                li.children.firstOrNull()?.let { liInner ->
//                    liInner as TextView
//                    @SuppressLint("SetTextI18n")
//                    liInner.text = innerContainer.childCount.toString()
//                }
//                innerContainer = li
//            }
//        }
//
//        content.children?.onEach { children ->
//            render(innerContainer, children)
//        }
//    }

    private fun blockExpand(isExpanded: Boolean, textView: TextView) {
        val postfix = if (!isExpanded) "collapsed" else "expanded"
        val parent = textView.parent as ViewGroup
        parent.children.asIterable().onEach { child ->
            if (child.tag.toString().startsWith("block_title_")) {
                child.tag = "block_title_$postfix"
                expandImage(child as TextView, isExpanded)
            } else {
                child.visibility =
                    if (isExpanded) View.VISIBLE else View.GONE
            }
        }
    }

    private fun expandImage(textView: TextView, isExpand: Boolean) {
        textView.setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            ResourcesCompat.getDrawable(
                resources,
                if (isExpand) R.drawable.ic_box else R.drawable.ic_box1,
                theme
            ),
            null
        )
    }

}