package com.randomeququiz

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog


class GamePlayActivity : AppCompatActivity(), View.OnClickListener {
    private var leftEq_text: TextView? = null
    private var rightEq_text: TextView? = null
    private lateinit var timer_text: TextView
    private lateinit var left_equ: String
    private  lateinit var right_equ: String
    private var score = 0
    private lateinit var timer: MyCountdownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameplay)
        leftEq_text = findViewById(R.id.leftEq_text)
        rightEq_text = findViewById(R.id.rightEq_text)
        timer_text = findViewById(R.id.timer_text)
        val less_btn = findViewById<Button>(R.id.less_btn)
        less_btn.setOnClickListener(this)
        val equal_btn = findViewById<Button>(R.id.equal_btn)
        equal_btn.setOnClickListener(this)
        val greater_btn = findViewById<Button>(R.id.greater_btn)
        greater_btn.setOnClickListener(this)
        if (savedInstanceState != null) {
            score = savedInstanceState.getInt("score")
            left_equ = savedInstanceState.getString("left_equ").toString()
            right_equ = savedInstanceState.getString("right_equ").toString()
        }
        next()
        timer = MyCountdownTimer(50000, 1000, timer_text, score, applicationContext)
        timer.start()
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
        bundle.putInt("score", score)
        bundle.putString("left_equ", left_equ)
        bundle.putString("right_equ", right_equ)
    }

    private fun showToast(isCorrect: Boolean) {
        val dialog = AlertDialog.Builder(this).create()
        // set the custom layout
        val customLayout = layoutInflater.inflate(R.layout.dialogbox, null)
        dialog.setView(customLayout)
        val statusText = customLayout.findViewById<TextView>(R.id.statusText)
        val nextBtn = customLayout.findViewById<Button>(R.id.nextBtn)
        nextBtn.setOnClickListener {
            next()
            dialog.cancel()
        }

        if (isCorrect) {

            score++

            if (score % 5 == 0 ) {
                println("edsds sd$score")

                val countdownPeriod = timer.countdownPeriod
                timer.cancel()
                timer = MyCountdownTimer(
                    countdownPeriod + 10000,
                    1000,
                    timer_text,
                    score,
                    applicationContext
                )
                timer.start()
            }
            statusText.text = "CORRECT!"
            statusText.setTextColor(resources.getColor(android.R.color.holo_green_light))
        } else {
            statusText.text = "WRONG!"
            statusText.setTextColor(resources.getColor(android.R.color.holo_red_light))
        }
        dialog.setCancelable(false)
        dialog.show()
    }

    private operator fun next() {
        val randomQuestionLists = Utils().generatedRandomQuestions
        leftEq_text!!.text = randomQuestionLists[0].toString()
        rightEq_text!!.text = randomQuestionLists[1].toString()
        left_equ = randomQuestionLists[0].toString()
        right_equ = randomQuestionLists[1].toString()
    }

    override fun onClick(view: View) {
        when {
            R.id.less_btn == view.id -> showToast(Utils.eval(left_equ) > Utils.eval(right_equ))
            R.id.equal_btn == view.id -> {
                showToast(Utils.eval(left_equ) == Utils.eval(right_equ))
            }
            R.id.greater_btn == view.id -> {
                showToast(Utils.eval(left_equ) < Utils.eval(right_equ))
            }
        }
    }
}