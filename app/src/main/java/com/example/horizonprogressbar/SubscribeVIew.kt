package com.example.horizonprogressbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.horizonprogressbar.databinding.SubscribeBinding
import java.util.function.BinaryOperator

class SubscribeView(context: Context, attributeSet: AttributeSet?) : LinearLayout(context, attributeSet) {
    private lateinit var binding: SubscribeBinding

    init {
        val inflater = LayoutInflater.from(context).inflate(R.layout.subscribe, this)
        binding = SubscribeBinding.bind(inflater)
        this.orientation = VERTICAL
        binding.btnSubscribe.setOnClickListener {
            Toast.makeText(context, binding.etSubscribe.text, Toast.LENGTH_SHORT).show()
        }
    }
}