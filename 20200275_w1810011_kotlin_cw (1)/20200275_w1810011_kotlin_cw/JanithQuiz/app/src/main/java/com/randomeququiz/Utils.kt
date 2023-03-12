package com.randomeququiz


import com.randomeququiz.QuestionList
import com.randomeququiz.Question
import java.lang.RuntimeException
import java.util.*

/**
 * References used
 * https://stackoverflow.com/questions/60930185/random-math-question-generator-not-following-while-loop
 * https://stackoverflow.com/questions/15210991/syntax-error-on-token-expected-when-initializing-called-value-from-anothe
 * https://stackoverflow.com/questions/17358255/need-help-making-a-random-math-generator
 */
class Utils {
    private val randomGenerator = Random()
    val generatedRandomQuestions: List<QuestionList>
        get() {
            val randomQuestionLists: MutableList<QuestionList> = ArrayList(NUMBER_OF_QUESTIONS)
            for (i in 0 until NUMBER_OF_QUESTIONS) {
                val randomQuestionElementsCapacity = randomQuestionElementsCapacity
                val questionList = QuestionList(randomQuestionElementsCapacity)
                for (j in 0 until randomQuestionElementsCapacity) {
                    val isLastIteration = j + 1 == randomQuestionElementsCapacity
                    val question = Question()
                    question.setValue(randomQuestionElementValue)
                    question.setOperator(
                        if (isLastIteration) null else Operator.values()[randomGenerator.nextInt(
                            Operator.values().size
                        )]
                    )
                    questionList.addElement(question)
                }
                randomQuestionLists.add(questionList)
            }
            return randomQuestionLists
        }
    private val randomQuestionElementsCapacity: Int
        private get() = getRandomIntegerFromRange(MIN_QUESTION_ELEMENTS, MAX_QUESTION_ELEMENTS)
    private val randomQuestionElementValue: Int
        private get() = getRandomIntegerFromRange(
            MIN_QUESTION_ELEMENT_VALUE,
            MAX_QUESTION_ELEMENT_VALUE
        )

    private fun getRandomIntegerFromRange(min: Int, max: Int): Int {
        return randomGenerator.nextInt(max - min + 1) + min
    }

    companion object {
        private const val NUMBER_OF_QUESTIONS = 2
        private const val MIN_QUESTION_ELEMENTS = 2
        private const val MAX_QUESTION_ELEMENTS = 4
        private const val MIN_QUESTION_ELEMENT_VALUE = 1
        private const val MAX_QUESTION_ELEMENT_VALUE = 20
        fun eval(str: String): Double {
            return object : Any() {
                var pos = -1
                var ch = 0
                fun nextChar() {
                    ch = if (++pos < str.length) str[pos].code else -1
                }

                fun eat(charToEat: Int): Boolean {
                    while (ch == ' '.code) nextChar()
                    if (ch == charToEat) {
                        nextChar()
                        return true
                    }
                    return false
                }

                fun parse(): Double {
                    nextChar()
                    val x = parseExpression()
                    if (pos < str.length) throw RuntimeException("Unexpected: " + ch.toChar())
                    return x
                }

                fun parseExpression(): Double {
                    var x = parseTerm()
                    while (true) {
                        if (eat('+'.code)) x += parseTerm() // addition
                        else if (eat('-'.code)) x -= parseTerm() // subtraction
                        else return x
                    }
                }

                fun parseTerm(): Double {
                    var x = parseFactor()
                    while (true) {
                        if (eat('*'.code)) x *= parseFactor() // multiplication
                        else if (eat('/'.code)) x /= parseFactor() // division
                        else return x
                    }
                }

                fun parseFactor(): Double {
                    if (eat('+'.code)) return +parseFactor() // unary plus
                    if (eat('-'.code)) return -parseFactor() // unary minus
                    var x: Double
                    val startPos = pos
                    if (eat('('.code)) { // parentheses
                        x = parseExpression()
                        if (!eat(')'.code)) throw RuntimeException("Missing ')'")
                    } else if (ch >= '0'.code && ch <= '9'.code || ch == '.'.code) { // numbers
                        while (ch >= '0'.code && ch <= '9'.code || ch == '.'.code) nextChar()
                        x =
                            str.substring(startPos, pos).toDouble()
                    } else if (ch >= 'a'.code && ch <= 'z'.code) { // functions
                        while (ch >= 'a'.code && ch <= 'z'.code) nextChar()
                        val func = str.substring(startPos, pos)
                        if (eat('('.code)) {
                            x = parseExpression()
                            if (!eat(')'.code)) throw RuntimeException("Missing ')' after argument to " + func)
                        } else {
                            x = parseFactor()
                        }
                        when (func) {
                            "sqrt" -> x = Math.sqrt(x)
                            "sin" -> x =
                                Math.sin(Math.toRadians(x))
                            "cos" -> x =
                                Math.cos(Math.toRadians(x))
                            "tan" -> x =
                                Math.tan(Math.toRadians(x))
                            else -> throw RuntimeException("Unknown function: " + func)
                        }
                    } else {
                        throw RuntimeException("Unexpected: " + ch.toChar())
                    }
                    if (eat('^'.code)) x =
                        Math.pow(x, parseFactor()) // exponentiation
                    return x
                }
            }.parse()
        }
    }
}