package com.randomeququiz

import android.widget.TextView
import android.os.CountDownTimer
import android.content.Intent
import com.randomeququiz.PointsActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.randomeququiz.R
import com.randomeququiz.DashboardActivity
import com.randomeququiz.Question

class PointsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_points)
        val score_text = findViewById<TextView>(R.id.score_text)
        score_text.text = "You have scored : " + intent.getIntExtra("score", 0)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, DashboardActivity::class.java))
    }
}