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
import java.lang.StringBuilder
import java.util.ArrayList

class QuestionList(sizeOfQuestionElemets: Int) {
    private val questions: MutableList<Question>
    fun addElement(question: Question) {
        questions.add(question)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (question in questions) {
            sb.append(question)
        }
        return sb.toString().trim { it <= ' ' }
    }

    init {
        questions = ArrayList(sizeOfQuestionElemets)
    }
}