package com.randomeququiz

import android.content.Context
import android.widget.TextView
import android.os.CountDownTimer
import android.content.Intent
import com.randomeququiz.PointsActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.randomeququiz.R
import com.randomeququiz.DashboardActivity
import com.randomeququiz.Question

class MyCountdownTimer(
    var countdownPeriod: Long,
    countDownInterval: Long,
    private val textView3: TextView,
    private val score: Int,
    private val c: Context
) : CountDownTimer(
    countdownPeriod, countDownInterval
) {
    override fun onTick(millisUntilFinished: Long) {
        textView3.text = "Timer : " + millisUntilFinished / 1000
        countdownPeriod = millisUntilFinished
    }

    override fun onFinish() {
        val i = Intent(c, PointsActivity::class.java)
        i.putExtra("score", score)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        c.startActivity(i)
    }
}