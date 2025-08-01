package com.example.tapemojigame

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var emoji: TextView
    private lateinit var tvScore: TextView
    private lateinit var tvTimer: TextView
    private lateinit var root: ConstraintLayout
    private lateinit var minusbtn: TextView
    private var score = 0
    private val random = Random
    private val emojis = listOf("😀", "😈", "👽", "🤖", "🦊", "🐵", "👻", "😎")

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        minusbtn = findViewById(R.id.minusBB)
        root = findViewById(R.id.roootlayout)
        emoji = findViewById(R.id.emojiView)
        tvScore = findViewById(R.id.tvScore)
        tvTimer = findViewById(R.id.tvTimer)

        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels

        emoji.setOnClickListener {
            score++
            tvScore.text = "Score: $score"
            moveEmojiRandomly(screenWidth, screenHeight)
            minusbtn.visibility = View.GONE
        }
        root.setOnClickListener {
            score--
            tvScore.text = "Score:$score"
            minusbtn.visibility = View.VISIBLE

        }
        startTimer()
    }

    private fun moveEmojiRandomly(screenWidth: Int, screenHeight: Int) {
        val x = random.nextInt(screenWidth - 200)
        val y = random.nextInt(screenHeight - 400)
        emoji.x = x.toFloat()
        emoji.y = y.toFloat()
        emoji.text = emojis[random.nextInt(emojis.size)]
    }

    private fun startTimer() {
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvTimer.text = "Time: ${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                tvTimer.text = "Time: 0"
                emoji.isEnabled = false

                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Game Over!")
                    .setMessage("Your score: $score")
                    .setCancelable(false)
                    .setPositiveButton("Restart") { _, _ ->
                        score = 0
                        tvScore.text = "Score: $score"
                        emoji.isEnabled = true
                        startTimer()
                    }
                    .setNegativeButton("Cancel") { _, _ ->
                        finish()
                    }
                    .show()
            }

        }.start()
    }
}
