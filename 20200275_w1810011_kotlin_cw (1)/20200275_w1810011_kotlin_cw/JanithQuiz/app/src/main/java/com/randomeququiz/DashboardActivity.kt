package com.randomeququiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class DashboardActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        findViewById<Button>(R.id.start_game_btn).setOnClickListener(this)
         findViewById<Button>(R.id.about_btn).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.start_game_btn) {
            startActivity(Intent(this, GamePlayActivity::class.java))
        } else if (view.id == R.id.about_btn) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Name : Janith Kudawithana\nStudent Id: 20200275\n\nI confirm that I understand what plagiarism is and have read and understood the section on Assessment Offences in the Essential Information for Students. The work that I have submitted is entirely my own. Any work from other authors is duly referenced and acknowledged.")
                .setCancelable(true)
            val alert = builder.create()
            alert.show()
        }
    }
}