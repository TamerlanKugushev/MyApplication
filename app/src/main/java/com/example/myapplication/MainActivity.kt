package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.LayoutDiscountBannerBinding
import com.example.myapplication.databinding.LayoutDiscountInfoBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var layoutDiscountBannerBinding: LayoutDiscountBannerBinding
    lateinit var layoutDiscountInfoBinding: LayoutDiscountInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        layoutDiscountBannerBinding = LayoutDiscountBannerBinding.inflate(layoutInflater)
        layoutDiscountInfoBinding = LayoutDiscountInfoBinding.inflate(layoutInflater)

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