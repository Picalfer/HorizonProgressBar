package com.example.horizonprogressbar

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.AnimationDrawable
import android.media.JetPlayer.OnJetEventListener
import android.os.Bundle
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.ALPHA
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
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
import com.example.horizonprogressbar.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentProgress: Int = 0
    private var currentSuccess: Int = 0
    private var currentRotation: Float = 45F

    private var variety = 1
    private var dayNightMode = 1

    private var actionMode: ActionMode? = null

    @SuppressLint("AppCompatMethod", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        // todo for testing xml features
        test6()

        val ivAnimation = AnimationUtils.loadAnimation(this, R.anim.iv_animation)

        val animationFrame1 = resources.getDrawable(R.drawable.frame_animation) as AnimationDrawable
        val animationFrame2 = resources.getDrawable(R.drawable.frame_animation) as AnimationDrawable
        val animationFrame3 = resources.getDrawable(R.drawable.frame_animation) as AnimationDrawable
        val animationFrame4 = resources.getDrawable(R.drawable.frame_animation) as AnimationDrawable
        binding.ivLeftTop?.setBackgroundDrawable(animationFrame1)
        binding.ivLeftDown?.setBackgroundDrawable(animationFrame2)
        binding.ivRightTop?.setBackgroundDrawable(animationFrame3)
        binding.ivRightDown?.setBackgroundDrawable(animationFrame4)
        animationFrame1.start()
        animationFrame2.start()
        animationFrame3.start()
        animationFrame4.start()

        /*ivMain.setOnClickListener {
            it.startAnimation(ivAnimation)
        }*/

        Picasso.get()
            .load("https://raccoon-city.ru/wp-content/uploads/2021/06/kyb-eydwdya-e1622731650346.jpg")
            .placeholder(R.drawable.main_logo)
            .error(R.drawable.main_logo)
            .into(binding.ivMain)

        binding.progressBar.max = 100

        binding.topBar.setNavigationOnClickListener {
            Toast.makeText(this, "Someday there will be navigation...", Toast.LENGTH_SHORT).show()
        }

        binding.topBar.setOnMenuItemClickListener {
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

        binding.ivMain.setOnLongClickListener {
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

        binding.startProgress.setOnClickListener {

            if (currentProgress == 100) {
                if (variety == 1) {
                    binding.progressBar.setProgressDrawableTiled(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.custom_progress_bg_revert
                        )
                    )
                    binding.tvSuccess.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.progress_bar_bg
                        )
                    )
                    binding.iconRed.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.progress_bar_bg
                        )
                    )
                    variety = 2
                } else {
                    binding.progressBar.setProgressDrawableTiled(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.custom_progress_bg
                        )
                    )
                    binding.tvSuccess.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.progress_bar
                        )
                    )
                    binding.iconRed.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.progress_bar
                        )
                    )
                    variety = 1
                }
                currentProgress = 0
            }

            currentProgress += 10
            currentSuccess += 1
            currentRotation += 45

            if (currentSuccess % 10 == 0) {
                binding.ivMain.startAnimation(ivAnimation)
            }

            binding.tvSuccess.text = currentSuccess.toString()
            binding.progressBar.progress = currentProgress
            binding.iconRed.rotation = currentRotation
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
    private fun test5() {
        setContentView(R.layout.test5)

        val btnUp = findViewById<Button>(R.id.btn_up)
        val btnDown = findViewById<Button>(R.id.btn_down)
        val btnLeft = findViewById<Button>(R.id.btn_left)
        val btnRight = findViewById<Button>(R.id.btn_right)
        val btnRotateLeft = findViewById<Button>(R.id.btn_rotate_left)
        val btnRotateRight = findViewById<Button>(R.id.btn_rotate_right)
        val btnReset = findViewById<Button>(R.id.btn_reset)

        val ivHero = findViewById<ImageView>(R.id.iv_kopibara)

        btnLeft.setOnClickListener {
            val anim = ObjectAnimator.ofFloat(
                ivHero,
                View.TRANSLATION_X,
                ivHero.translationX,
                ivHero.translationX - 100
            )
            anim.duration = 200
            anim.start()
        }

        btnRight.setOnClickListener {
            val anim = ObjectAnimator.ofFloat(
                ivHero,
                View.TRANSLATION_X,
                ivHero.translationX,
                ivHero.translationX + 100
            )
            anim.duration = 200
            anim.start()
        }

        btnUp.setOnClickListener {
            val anim = ObjectAnimator.ofFloat(
                ivHero,
                View.TRANSLATION_Y,
                ivHero.translationY,
                ivHero.translationY - 100
            )
            anim.duration = 200
            anim.start()
        }

        btnDown.setOnClickListener {
            val anim = ObjectAnimator.ofFloat(
                ivHero,
                View.TRANSLATION_Y,
                ivHero.translationY,
                ivHero.translationY + 100
            )
            anim.duration = 200
            anim.start()
        }

        btnRotateLeft.setOnClickListener {
            val anim =
                ObjectAnimator.ofFloat(ivHero, View.ROTATION, ivHero.rotation, ivHero.rotation - 45)
            anim.duration = 200
            anim.start()
        }

        btnRotateRight.setOnClickListener {
            val anim =
                ObjectAnimator.ofFloat(ivHero, View.ROTATION, ivHero.rotation, ivHero.rotation + 45)
            anim.duration = 200
            anim.start()
        }

        btnReset.setOnClickListener {
            val animRotation = ObjectAnimator.ofFloat(ivHero, View.ROTATION, ivHero.rotation, 0F)
            animRotation.duration = 200
            animRotation.start()

            val animTranslationX =
                ObjectAnimator.ofFloat(ivHero, View.TRANSLATION_X, ivHero.translationX, 0F)
            animTranslationX.duration = 200
            animTranslationX.start()

            val animTranslationY =
                ObjectAnimator.ofFloat(ivHero, View.TRANSLATION_Y, ivHero.translationY, 0F)
            animTranslationY.duration = 200
            animTranslationY.start()
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

        val btnStart = findViewById<Button>(R.id.btn_start_animation)
        val btnDoAnim = findViewById<Button>(R.id.btn_do_animation)

        val animationUpdateListener = object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                Toast.makeText(this@MainActivity, "Animation start", Toast.LENGTH_SHORT).show()
                Toast.makeText(this@MainActivity, "${btnDoAnim.translationY}", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onAnimationEnd(animation: Animator) {
                Toast.makeText(this@MainActivity, "Animation End", Toast.LENGTH_SHORT).show()
                Toast.makeText(this@MainActivity, "${btnDoAnim.translationY}", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onAnimationCancel(animation: Animator) {
                Toast.makeText(this@MainActivity, "Animation cancel", Toast.LENGTH_SHORT).show()
            }

            override fun onAnimationRepeat(animation: Animator) {
                Toast.makeText(this@MainActivity, "Animation repeat", Toast.LENGTH_SHORT).show()
                println("start")
            }
        }

        btnStart.setOnClickListener {
            val anim = ObjectAnimator.ofFloat(
                btnDoAnim,
                View.TRANSLATION_Y,
                btnDoAnim.translationY,
                btnDoAnim.translationY + 1000F
            )
            anim.duration = 1000
            anim.addListener(animationUpdateListener)
            anim.start()
        }
    }

    private fun test6() {
        setContentView(R.layout.test6)
        val sun = findViewById<ImageView>(R.id.sun)
        val cloud1 = findViewById<ImageView>(R.id.cloud1)
        val cloud2 = findViewById<ImageView>(R.id.cloud2)
        val night = findViewById<View>(R.id.night)

        val sunAnim = ObjectAnimator.ofFloat(sun, View.TRANSLATION_Y, -1200f)
        sunAnim.duration = 1500

        val nightAnim = ObjectAnimator.ofFloat(night, View.ALPHA, 0f)
        nightAnim.duration = 2000

        val cloud1Anim = ObjectAnimator.ofFloat(cloud1, View.TRANSLATION_X, 700f)
        cloud1Anim.duration = 1000

        val cloud2Anim = ObjectAnimator.ofFloat(cloud2, View.TRANSLATION_X, -700f)
        cloud2Anim.duration = 1000

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(sunAnim, nightAnim)
        animatorSet.play(cloud1Anim).after(sunAnim)
        animatorSet.play(cloud2Anim).after(sunAnim)
        animatorSet.interpolator = BounceInterpolator()

        animatorSet.start()

        // test animation from ShapeShifter.design
        val animContainer = findViewById<ImageView>(R.id.anim_container)
        animContainer.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.avd_anim))

        val animation = animContainer.drawable as AnimatedVectorDrawable

        animContainer.setOnClickListener {
            animation.start()
        }
    }

    private fun changeTheme() {
        if (dayNightMode == 1) {
            binding.rootFrame.background =
                AppCompatResources.getDrawable(this, R.drawable.bg_red_gradient)
            binding.topBar.background =
                AppCompatResources.getDrawable(this, R.drawable.bg_red_gradient)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            dayNightMode = 2
        } else {
            binding.rootFrame.background =
                AppCompatResources.getDrawable(this, R.drawable.bg_gradient)
            binding.topBar.background = AppCompatResources.getDrawable(this, R.drawable.bg_gradient)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            dayNightMode = 2
        }
    }
}