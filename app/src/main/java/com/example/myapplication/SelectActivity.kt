package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import com.example.myapplication.databinding.ActivityMainBinding

class SelectActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        listOf(
            "SAMPLE 1" to R.raw.sample1,
            "SAMPLE 2" to R.raw.sample2
        ).forEach { (name, resource) ->
            binding.container.addView(
                AppCompatButton(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                    )
                    text = name
                    setOnClickListener {
                        startActivity(Intent(this@SelectActivity, MainActivity::class.java).apply {
                            putExtra("RESOURCE", resource)
                        })
                    }
                }
            )
        }


    }
}