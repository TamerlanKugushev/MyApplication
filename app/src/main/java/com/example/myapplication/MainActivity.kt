package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Matrix
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.example.example.Content
import com.example.example.ContentType
import com.example.example.MainContent
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    var isHidden1 = true
    var isHidden2 = true

    private val json = """
        { 
         "mainContent":[
           {
              "type":"paragraph",
              "children":[
                 {
                    "text":"Копите кешбэк и обменивайте его на скидки до 100% на любые покупки у наших партнеров.",
                    "type":"text"
                 }
              ]
           },
           {
              "type":"block",
              "title":"Как получить скидку",
              "children":[
                 {
                    "type":"ol",
                    "children":[
                       {
                          "type":"li",
                          "children":[
                             {
                                "text":"Выберите партнера на карте",
                                "type":"text"
                             }
                          ]
                       },
                       {
                          "type":"li",
                          "children":[
                             {
                                "text":"Закажите промокод",
                                "type":"text"
                             }
                          ]
                       },
                       {
                          "type":"li",
                          "children":[
                             {
                                "text":"Покажите его на кассе",
                                "type":"text"
                             }
                          ]
                       }
                    ]
                 }
              ]
           },
           {
              "type":"block",
              "title":"О проекте",
              "children":[
                 {
                    "text":"<a href=\"https://stage-znaem.mts.ru/\">МТС Знаем лично</a> - проект поддержки малого предпринимательства. Хотим, чтобы маленьких бизнесов было больше и они чувствовали себя лучше.",
                    "type":"text"
                 }
              ]
           }
        ]
       }
    """.trimIndent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        cropEndBanner()
        renderMainContent()
    }

    private fun renderMainContent() {
        val content = Gson().fromJson(json, MainContent::class.java)
        val container = binding.info.parentLayout

        content.mainContent?.onEach { render(container, it) }
    }

    private fun render(container: ViewGroup, content: Content) {
        var innerContainer = container
        when (content.type) {
            ContentType.PARAGRAPH -> {
                val viewGroup = layoutInflater.inflate(R.layout.paragraph, container) as ViewGroup
                innerContainer = viewGroup.children.last() as ViewGroup
            }

            ContentType.TEXT -> {
                val viewGroup = layoutInflater.inflate(R.layout.text, container) as ViewGroup
                viewGroup.children.last().apply {
                    this as TextView

                    movementMethod = LinkMovementMethod.getInstance()
                    text = Html.fromHtml(content.text)
                    removeLinksUnderline()
                }
            }

            ContentType.BLOCK -> {
                val viewGroup = layoutInflater.inflate(R.layout.block, container) as ViewGroup

                val block = viewGroup.children.last() as ViewGroup
                val blockTitle = block.children.first() as TextView
                val blockContent = block.children.last() as ViewGroup

                blockTitle.apply {
                    text = content.title
                    setOnClickListener { expandedView ->
                        val isExpanded = expandedView.tag == "block_title_expanded"
                        val postfix = if (isExpanded) "collapsed" else "expanded"
                        val parent = expandedView.parent as ViewGroup
                        parent.children.asIterable().onEach { child ->
                            if (child.tag.toString().startsWith("block_title_")) {
                                child.tag = "block_title_$postfix"
                                expandImage(child as TextView, !isExpanded)
                            } else {
                                child.visibility =
                                    if (isExpanded) View.GONE else View.VISIBLE
                            }
                        }
                    }
                }
                innerContainer = blockContent
            }

            ContentType.OL -> {
                val viewGroup = layoutInflater.inflate(R.layout.ol, container) as ViewGroup
                innerContainer = viewGroup.children.last() as ViewGroup
            }

            ContentType.LI -> {
                val viewGroup = layoutInflater.inflate(R.layout.li, container) as ViewGroup
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

    private fun TextView.removeLinksUnderline() {
        val spannable = SpannableString(text)
        for (u in spannable.getSpans(0, spannable.length, URLSpan::class.java)) {
            spannable.setSpan(
                object : URLSpan(u.url) {
                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                        ds.color = Color.BLUE
                    }
                },
                spannable.getSpanStart(u),
                spannable.getSpanEnd(u), 0
            )
        }
        text = spannable
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