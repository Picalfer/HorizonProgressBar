package com.example.horizonprogressbar

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.MaterialToolbar
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private var currentProgress: Int = 0
    private var currentSuccess: Int = 0
    private var currentRotation: Float = 45F
    private lateinit var progressBar: ProgressBar
    private lateinit var startProgress: Button
    private lateinit var textSuccess: TextView
    private lateinit var ivMain: ImageView
    private lateinit var iconRed: View
    private lateinit var navMenu: MaterialToolbar
    private lateinit var rootFrame: FrameLayout
    private var variety = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        startProgress = findViewById(R.id.startProgress)
        textSuccess = findViewById(R.id.tvSuccess)
        ivMain = findViewById(R.id.iv_main)
        iconRed = findViewById(R.id.icon_red)
        navMenu = findViewById(R.id.top_bar)
        rootFrame = findViewById(R.id.root_frame)

        // todo testing xml features
        // setContentView(R.layout.test)

        Picasso.get()
            .load("https://raccoon-city.ru/wp-content/uploads/2021/06/kyb-eydwdya-e1622731650346.jpg")
            .placeholder(R.drawable.main_logo)
            .error(R.drawable.main_logo)
            .into(ivMain)

        progressBar.max = 100

        navMenu.setNavigationOnClickListener {
            Toast.makeText(this, "Someday there will be navigation...", Toast.LENGTH_SHORT).show()
        }

        navMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.fav -> {
                    Toast.makeText(this, "Favorites", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.search -> {
                    changeTheme()
                    Toast.makeText(this, "Dark theme", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.more -> {
                    Toast.makeText(this, "More", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }

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
                    iconRed.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.progress_bar_bg
                        )
                    )
                    variety = 2
                } else {
                    progressBar.setProgressDrawableTiled(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.custom_progress_bg
                        )
                    )
                    textSuccess.setTextColor(ContextCompat.getColor(this, R.color.progress_bar))
                    iconRed.setBackgroundColor(ContextCompat.getColor(this, R.color.progress_bar))
                    variety = 1
                }
                currentProgress = 0
            }

            currentProgress += 10
            currentSuccess += 1
            currentRotation += 45

            textSuccess.text = currentSuccess.toString()
            progressBar.progress = currentProgress
            iconRed.rotation = currentRotation
        }
    }

    private fun changeTheme() {
        // TODO:
        // rootFrame.background = AppCompatResources.getDrawable(this, R.drawable.bg_red_gradient)
    }
}