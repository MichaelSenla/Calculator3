package com.example.calculator3

class Calculation {

    companion object {
        private const val PLUS = "+"
        private const val MINUS = "-"
        private const val MULTIPLICATION_STAR = "*"
        private const val DIVISION = "/"
    }

    fun calculatePlusMinus(passedList: MutableList<Any>): Int {
        var result = passedList[0] as Int
        for (i in passedList.indices) {
            if (passedList[i] is Char && i != passedList.lastIndex) {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Int
                if (operator == PLUS) {
                    result += nextDigit
                }
                if (operator == MINUS) {
                    result -= nextDigit
                }
            }
        }
        return result
    }

    fun finishCalculationTimesDivision(passedList: MutableList<Any>): MutableList<Any> {
        var list = passedList
        while (list.contains(MULTIPLICATION_STAR) || list.contains(DIVISION)
        ) {
            list = startCalculationTimesDivision(list)
        }
        return list
    }

    private fun startCalculationTimesDivision(passedList: MutableList<Any>): MutableList<Any> {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size

        for (i in passedList.indices) {
            if (passedList[i] is Char && i != passedList.lastIndex && i < restartIndex) {
                val operator = passedList[i]
                val prevDigit = passedList[i - 1] as Int
                val nextDigit = passedList[i + 1] as Int
                when (operator) {
                    MULTIPLICATION_STAR -> {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    DIVISION -> {
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    else -> {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }
            if (i > restartIndex) {
                newList.add(passedList[i])
            }
        }
        return newList
    }

    fun separateInputLine(input: String): MutableList<Any> {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for (character in input) {
            if (character.isDigit()) {
                currentDigit += character
            } else {
                list.add(currentDigit.toInt())
                currentDigit = ""
                list.add(character)
            }
        }
        if (currentDigit != "") {
            list.add(currentDigit.toInt())
        }
        return list
    }
}