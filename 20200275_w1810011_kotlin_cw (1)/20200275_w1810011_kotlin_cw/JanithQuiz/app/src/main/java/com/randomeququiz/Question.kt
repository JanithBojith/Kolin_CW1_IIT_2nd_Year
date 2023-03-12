package com.randomeququiz



class Question {
    private var value = 0
    private var operator: Operator? = null
    fun setValue(value: Int) {
        this.value = value
    }

    fun setOperator(operator: Operator?) {
        this.operator = operator
    }

    override fun toString(): String {
        return value.toString() + (if (operator == null) "" else " " + operator!!.displayValue) + " "
    }
}