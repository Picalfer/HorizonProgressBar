package com.example.horizonprogressbar

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.ALPHA
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
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
    private var dayNightMode = 1

    private var actionMode: ActionMode? = null

    @SuppressLint("AppCompatMethod", "MissingInflatedId")
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

        // todo for testing xml features
        test4()

        val ivAnimation = AnimationUtils.loadAnimation(this, R.anim.iv_animation)

        /*ivMain.setOnClickListener {
            it.startAnimation(ivAnimation)
        }*/

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

        ivMain.setOnLongClickListener {
            if (actionMode != null) {
                return@setOnLongClickListener false
            }

            actionMode = startActionMode(object : ActionMode.Callback {
                override fun onCreateActionMode(
                    mode: ActionMode?,
                    menu: Menu?,
                ): Boolean {
                    val inflater = mode?.menuInflater
                    inflater?.inflate(R.menu.context_menu, menu)
                    mode?.title = "Select option"
                    return true
                }

                override fun onPrepareActionMode(
                    mode: ActionMode?,
                    menu: Menu?,
                ): Boolean {
                    return false
                }

                override fun onActionItemClicked(
                    mode: ActionMode?,
                    item: MenuItem?,
                ): Boolean {
                    when (item?.itemId) {
                        R.id.copy -> Toast.makeText(this@MainActivity, "Copy", Toast.LENGTH_SHORT)
                            .show()

                        R.id.search_in_web -> Toast.makeText(
                            this@MainActivity,
                            "Search",
                            Toast.LENGTH_SHORT
                        ).show()

                        R.id.download -> Toast.makeText(
                            this@MainActivity,
                            "Search in web",
                            Toast.LENGTH_SHORT
                        ).show()

                        R.id.more -> Toast.makeText(this@MainActivity, "More", Toast.LENGTH_SHORT)
                            .show()
                    }
                    return true
                }

                override fun onDestroyActionMode(mode: ActionMode?) {
                    actionMode = null
                }

            })
            return@setOnLongClickListener true
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

            if (currentSuccess % 10 == 0) {
                ivMain.startAnimation(ivAnimation)
            }

            textSuccess.text = currentSuccess.toString()
            progressBar.progress = currentProgress
            iconRed.rotation = currentRotation
        }
    }

    private fun test3() {
        setContentView(R.layout.test3)
        val btn1 = findViewById<Button>(R.id.button1)
        val btn2 = findViewById<Button>(R.id.button2)
        val btn3 = findViewById<Button>(R.id.button3)
        val btn4 = findViewById<Button>(R.id.button4)

        val btns = listOf<Button>(btn1, btn2, btn3)

        btns.forEach { btn ->
            btn.setOnClickListener {
                // создание анимации в коде
                val rotationAnimation =
                    AnimationUtils.loadAnimation(this, R.anim.rotation_animation)
                it.startAnimation(rotationAnimation)

                btn.animate()
                    .setDuration(300)
                    .alpha(0f)
                    .start()


                // привязка анимации из xml к элементу
                /*val myAnimation =
                    AnimationUtils.loadAnimation(this, R.anim.buttons_appear_animation)
                it.startAnimation(myAnimation)*/
            }
        }

        btn4.setOnClickListener {
            val increaseAnimation = AnimationUtils.loadAnimation(this, R.anim.increase_animation)

            btns.forEach {
                it.startAnimation(increaseAnimation)
                it.animate()
                    .setDuration(600)
                    .alpha(1f)
                    .start()
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun test4() {
        setContentView(R.layout.test4)
        val rocket = findViewById<ImageView>(R.id.rocket)

        val rocketAnimation = AnimationUtils.loadAnimation(this, R.anim.rocker_animation)

        rocket.setOnClickListener {
            it.startAnimation(rocketAnimation)
            ObjectAnimator.ofFloat(it, ALPHA, 1f, 0f)
                .setDuration(500)
                .start()
        }

        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.addUpdateListener {
            rocket.alpha = it.animatedValue as Float
        }
        animator.duration = 500
        animator.startDelay = 1000
        animator.start()
    }

    private fun changeTheme() {
        if (dayNightMode == 1) {
            rootFrame.background = AppCompatResources.getDrawable(this, R.drawable.bg_red_gradient)
            navMenu.background = AppCompatResources.getDrawable(this, R.drawable.bg_red_gradient)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            dayNightMode = 2
        } else {
            rootFrame.background = AppCompatResources.getDrawable(this, R.drawable.bg_gradient)
            navMenu.background = AppCompatResources.getDrawable(this, R.drawable.bg_gradient)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            dayNightMode = 2
        }
    }
}