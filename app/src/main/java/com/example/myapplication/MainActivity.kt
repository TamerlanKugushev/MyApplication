package com.example.myapplication

import android.graphics.Color
import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    var isHidden1 = true
    var isHidden2 = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with (binding.info) {

            textViewHowGetDiscount.setOnClickListener {
                if (isHidden1) {
                    hidden.visibility = View.GONE
                } else {
                    hidden.visibility = View.VISIBLE
                }
                isHidden1 = !isHidden1
                expandImage(textViewHowGetDiscount, isHidden1)
            }

            textViewProject.setOnClickListener {
                if (isHidden2) {
                    hidden2.visibility = View.GONE
                } else {
                    hidden2.visibility = View.VISIBLE
                }
                isHidden2 = !isHidden2
                expandImage(textViewProject, isHidden2)
            }
        }

        cropEndBanner()
        setHtml()
    }

    private fun setHtml() {
        val html = "<a href=\"https://stage-znaem.mts.ru/\">МТС Знаем лично</a> - проект поддержки малого предпринимательства. Хотим, чтобы маленьких бизнесов было больше и они чувствовали себя лучше."

        binding.info.textViewMTS.movementMethod = LinkMovementMethod.getInstance()
        binding.info.textViewMTS.text = Html.fromHtml(html)
        binding.info.textViewMTS.removeLinksUnderline()
    }

    private fun TextView.removeLinksUnderline() {
        val spannable = SpannableString(text)
        for (u in spannable.getSpans(0, spannable.length, URLSpan::class.java)) {
            spannable.setSpan(object : URLSpan(u.url) {
                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                    ds.color = Color.BLUE
                }
            },
                spannable.getSpanStart(u),
                spannable.getSpanEnd(u), 0)
        }
        text = spannable
    }

    private fun expandImage(textView: TextView, isExpand: Boolean) {
        textView.setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            ResourcesCompat.getDrawable(resources,
                if (isExpand) R.drawable.ic_box else R.drawable.ic_box1,
                theme),
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