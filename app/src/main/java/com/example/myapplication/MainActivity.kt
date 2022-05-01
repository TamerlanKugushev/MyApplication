package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.LayoutDiscountBannerBinding
import com.example.myapplication.databinding.LayoutDiscountInfoBinding

class MainActivity : AppCompatActivity() {

    private val activityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val layoutDiscountBannerBinding by lazy { LayoutDiscountBannerBinding.inflate(layoutInflater) }
    private val layoutDiscountInfoBinding by lazy { LayoutDiscountInfoBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

//        layoutDiscountInfoBinding.textViewHowGetDiscount.setOnClickListener {
//            if (layoutDiscountInfoBinding.hidden.visibility == View.VISIBLE) {
//                //TransitionManager.beginDelayedTransition(parentLayout, AutoTransition())
//                layoutDiscountInfoBinding.hidden.visibility = View.GONE
//                layoutDiscountInfoBinding.textViewHowGetDiscount.setCompoundDrawablesWithIntrinsicBounds(
//                    null,
//                    null,
//                    resources.getDrawable(R.drawable.ic_box),
//                    null
//                )
//            } else {
//                //TransitionManager.beginDelayedTransition(parentLayout, AutoTransition())
//                layoutDiscountInfoBinding.hidden.visibility = View.VISIBLE
//                layoutDiscountInfoBinding.textViewHowGetDiscount.setCompoundDrawablesWithIntrinsicBounds(
//                    null,
//                    null,
//                    resources.getDrawable(R.drawable.ic_box1),
//                    null
//                )
//            }
//        }
//
//        layoutDiscountInfoBinding.textViewProject.setOnClickListener {
//            if (layoutDiscountInfoBinding.hidden2.visibility == View.VISIBLE) {
//                //TransitionManager.beginDelayedTransition(parentLayout, AutoTransition())
//                layoutDiscountInfoBinding.hidden2.visibility = View.GONE
//                layoutDiscountInfoBinding.textViewProject.setCompoundDrawablesWithIntrinsicBounds(
//                    null,
//                    null,
//                    resources.getDrawable(R.drawable.ic_box),
//                    null
//                )
//            } else {
//                //TransitionManager.beginDelayedTransition(parentLayout, AutoTransition())
//                layoutDiscountInfoBinding.hidden2.visibility = View.VISIBLE
//                layoutDiscountInfoBinding.textViewProject.setCompoundDrawablesWithIntrinsicBounds(
//                    null,
//                    null,
//                    resources.getDrawable(R.drawable.ic_box1),
//                    null
//                )
//            }
//        }

    }
}