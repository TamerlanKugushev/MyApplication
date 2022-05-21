package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Matrix
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.getSpans
import androidx.core.view.children
import com.example.example.Content
import com.example.example.ContentType
import com.example.example.MainContent
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val json = """
{"mainContent":[
  {
    "type": "paragraph",
    "isOpen": true,
    "children": [
      {
        "text": "Копите кешбэк и обменивайте его на скидки до 100% на любые покупки у наших партнеров.\n",
        "type": "text"
      }
    ]
  },
  {
    "type": "block",
    "title": "Как получить скидку",
    "isOpen": true,
    "children": [
      {
        "type": "ol",
        "children": [
          {
            "type": "li",
            "children": [
              {
                "text": "Выберите партнера на карте",
                "type": "text"
              }
            ]
          },
          {
            "type": "li",
            "children": [
              {
                "text": "Закажите промокод",
                "type": "text"
              }
            ]
          },
          {
            "type": "li",
            "children": [
              {
                "text": "Покажите его на кассе",
                "type": "text"
              }
            ]
          }
        ]
      }
    ]
  },
  {
    "type": "block",
    "title": "О проекте",
    "isOpen": false,
    "children": [
      {
        "url": "https://stage-znaem.mts.ru/",
        "type": "link",
        "children": [
          {
            "text": "МТС Знаем лично",
            "type": "text"
          }
        ]
      },
      {
        "text": " - проект поддержки малого предпринимательства. Хотим, чтобы маленьких бизнесов было больше и они чувствовали себя лучше. Партнеры проекта \"МТС Знаем лично\" могут стать вашими любимыми местами для ежедневных покупок и отдыха.",
        "type": "text"
      }
    ]
  }
]}
    """.trimIndent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        cropEndBanner()
        renderMainContent()
        assets
    }

    private fun renderMainContent() {
        val content = Gson().fromJson(json, MainContent::class.java)
        val container = binding.info.parentLayout

        content.mainContent?.onEach { render(container, it) }
    }

    private fun render(container: ViewGroup, content: Content) {
        val layoutInflater = LayoutInflater.from(this)
        var innerContainer = container
        when (content.type) {
            ContentType.PARAGRAPH -> {
                val viewGroup =
                    layoutInflater.inflate(R.layout.layout_paragraph, container) as ViewGroup
                innerContainer = viewGroup.children.last() as ViewGroup
            }

            ContentType.TEXT -> {
                if (container.children.lastOrNull() is TextView
                    && container.children.last().tag == "text"
                ) {
                    val textView = container.children.last() as TextView
                    val spans = (textView.text as SpannableString).getSpans<Any>(
                        0,
                        textView.text.length
                    )

                    val span = SpannableStringBuilder().append(textView.text.toString())
                        .append(content.text)

                    spans.onEach {
                        span.setSpan(it, 0, textView.text.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
                    }

                    textView.text = span
                } else {
                    val viewGroup =
                        layoutInflater.inflate(R.layout.layout_text_html, container) as ViewGroup
                    viewGroup.children.last().apply {
                        this as TextView
                        text = SpannableString(content.text)
                    }
                }
            }

            ContentType.LINK -> {
                val viewGroup =
                    layoutInflater.inflate(R.layout.layout_text_html, container) as ViewGroup
                viewGroup.children.last().apply {
                    this as TextView
                    val link = content.children?.firstOrNull()?.text
                    val span = SpannableString(link)
                    movementMethod = LinkMovementMethod.getInstance()
                    span.setSpan(object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            //intents.onNext(DiscountView.Intent.OpenUrl(content.url))
                            Toast.makeText(widget.context, "CLICK", Toast.LENGTH_LONG).show()
                        }
                    }, 0, link?.length ?: 0, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
                    span.setSpan(
                        ForegroundColorSpan(Color.BLUE),
                        0, link?.length ?: 0, Spanned.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    span.setSpan(
                        object : UnderlineSpan() {
                            override fun updateDrawState(tp: TextPaint) {
                                tp.isUnderlineText = false
                            }
                        },
                        0, link?.length ?: 0, Spanned.SPAN_INCLUSIVE_INCLUSIVE
                    )

                    text = span
                }
                return
            }

            ContentType.BLOCK -> {
                val viewGroup =
                    layoutInflater.inflate(R.layout.layout_block_title, container) as ViewGroup

                val block = viewGroup.children.last() as ViewGroup
                val blockTitle = block.children.first() as TextView
                val blockContent = block.children.last() as ViewGroup

                blockExpand(content.isOpen ?: true, blockTitle)

                blockTitle.apply {
                    text = content.title

                    setOnClickListener { expandedView ->
//                        analytics.log(
//                            null,
//                            DiscountEvent.ExpandedBlockClickEvent(
//                                text.toString().toTranslit()
//                            )
//                        )
                        val isExpanded = expandedView.tag == "block_title_expanded"
                        blockExpand(!isExpanded, expandedView as TextView)
                    }
                }
                innerContainer = blockContent
            }

            ContentType.OL -> {
                val viewGroup =
                    layoutInflater.inflate(R.layout.layout_block_item_ol, container) as ViewGroup
                innerContainer = viewGroup.children.last() as ViewGroup
            }

            ContentType.LI -> {
                val viewGroup =
                    layoutInflater.inflate(R.layout.layout_block_item_li, container) as ViewGroup
                val li = viewGroup.children.last() as ViewGroup
                li.children.firstOrNull()?.let { liInner ->
                    liInner as TextView
                    @SuppressLint("SetTextI18n")
                    liInner.text = innerContainer.childCount.toString()
                }
                innerContainer = li
            }
        }

        content.children?.onEach { children ->
            render(innerContainer, children)
        }
    }

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

    private fun cropEndBanner() {
        val baseMatrix = Matrix()

        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.img, theme)!!

        val viewWidth: Float = resources.displayMetrics.widthPixels.toFloat()
        val viewHeight: Float = resources.getDimension(R.dimen.banner_height)
        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight

        val widthScale = viewWidth / drawableWidth
        val heightScale = viewHeight / drawableHeight

        val scale = widthScale.coerceAtLeast(heightScale)
        baseMatrix.postScale(scale, scale)
        baseMatrix.postTranslate(
            (viewWidth - drawableWidth * scale),
            (viewHeight - drawableHeight * scale)
        )
        binding.banner.imageViewBackground.imageMatrix = baseMatrix
    }

}