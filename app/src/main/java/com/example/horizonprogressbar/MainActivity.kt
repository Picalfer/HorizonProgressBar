package com.example.horizonprogressbar

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private var currentProgress: Int = 0
    private var currentSuccess: Int = 0
    private lateinit var progressBar: ProgressBar
    private lateinit var startProgress: Button
    private lateinit var textSuccess: TextView
    private var variety = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        startProgress = findViewById(R.id.startProgress)
        textSuccess = findViewById(R.id.tvSuccess)

        progressBar.max = 100

        startProgress.setOnClickListener {

            if (currentProgress == 100) {
                if (variety == 1) {
                    progressBar.setProgressDrawableTiled(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.custom_progress_bg_revert
                        )
                    )
                    textSuccess.setTextColor(ContextCompat.getColor(this, R.color.progress_bar_bg))
                    variety = 2
                } else {
                    progressBar.setProgressDrawableTiled(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.custom_progress_bg
                        )
                    )
                    textSuccess.setTextColor(ContextCompat.getColor(this, R.color.progress_bar))
                    variety = 1
                }
                currentProgress = 0
            }

            currentProgress += 10
            currentSuccess += 1
            textSuccess.text = currentSuccess.toString()
            progressBar.progress = currentProgress

        }
    }
}