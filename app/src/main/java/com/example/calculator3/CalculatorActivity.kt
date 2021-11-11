package com.example.calculator3

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator3.databinding.ActivityCalculatorBinding

class CalculatorActivity : AppCompatActivity() {

    companion object {
        private const val RESULT_KEY = "RESULT_KEY"
        private const val MATH_OPERATIONS_KEY = "MATH_OPERATIONS_KEY"
        private const val EXTRA_RESULT = "RESULT"
        private const val DIVISION_BY_ZERO = "/0"
    }

    private lateinit var binding: ActivityCalculatorBinding
    private var canAddOperation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setInputListeners()
        setOkayButtonListener()
    }

    private fun setInputListeners() {

        binding.buttonZero.setOnClickListener {
            addNumberText(binding.buttonZero)
        }
        binding.buttonOne.setOnClickListener {
            addNumberText(binding.buttonOne)
        }
        binding.buttonTwo.setOnClickListener {
            addNumberText(binding.buttonTwo)
        }
        binding.buttonThree.setOnClickListener {
            addNumberText(binding.buttonThree)
        }
        binding.buttonFour.setOnClickListener {
            addNumberText(binding.buttonFour)
        }
        binding.buttonFive.setOnClickListener {
            addNumberText(binding.buttonFive)
        }
        binding.buttonSix.setOnClickListener {
            addNumberText(binding.buttonSix)
        }
        binding.buttonSeven.setOnClickListener {
            addNumberText(binding.buttonSeven)
        }
        binding.buttonEight.setOnClickListener {
            addNumberText(binding.buttonEight)
        }
        binding.buttonNine.setOnClickListener {
            addNumberText(binding.buttonNine)
        }
        binding.division.setOnClickListener {
            addOperationText(binding.division)
        }
        binding.multiplication.setOnClickListener {
            addOperationText(binding.multiplication)
        }
        binding.subtraction.setOnClickListener {
            addOperationText(binding.subtraction)
        }
        binding.addition.setOnClickListener {
            addOperationText(binding.addition)
        }
        binding.clearButton.setOnClickListener {
            clearTextViews()
        }
        binding.equality.setOnClickListener {
            calculateResult()
        }
    }

    private fun setOkayButtonListener() {
        binding.okayButton.setOnClickListener {
            setResult(RESULT_OK, intent.apply {
                putExtra(EXTRA_RESULT, binding.resultText.text)
            })
            finish()
        }
    }

    private fun addNumberText(button: Button) {
        binding.mathOperations.append(button.text)
        canAddOperation = true
    }

    private fun addOperationText(button: Button) {
        if (canAddOperation) {
            binding.mathOperations.append(button.text)
            canAddOperation = false
        }
    }

    private fun clearTextViews() {
        binding.mathOperations.text = ""
        binding.resultText.text = ""
    }

    private fun calculateResult() {
        if (binding.mathOperations.text.contains(DIVISION_BY_ZERO)) {
            binding.equality.text = getString(R.string.activity_calculator_division_by_zero)
        } else {
            if (separateInputLine().isEmpty()) {
                binding.resultText.text = ""
            }
            val timesDivision = finishCalculationTimesDivision(separateInputLine())
            if (timesDivision.isEmpty()) {
                binding.resultText.text = ""
            }
            binding.resultText.text = calculatePlusMinus(timesDivision).toString()
        }
    }

    private fun calculatePlusMinus(passedList: MutableList<Any>): Int {
        var result = passedList[0] as Int
        for (i in passedList.indices) {
            if (passedList[i] is Char && i != passedList.lastIndex) {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Int
                if (operator == '+') {
                    result += nextDigit
                }
                if (operator == '-') {
                    result -= nextDigit
                }
            }
        }
        return result
    }

    private fun finishCalculationTimesDivision(passedList: MutableList<Any>): MutableList<Any> {
        var list = passedList
        while (list.contains('*') || list.contains('/')) {
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
                    '*' -> {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '/' -> {
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

    private fun separateInputLine(): MutableList<Any> {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for (character in binding.mathOperations.text) {
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(MATH_OPERATIONS_KEY, binding.mathOperations.text.toString())
        outState.putString(RESULT_KEY, binding.resultText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        binding.mathOperations.text = savedInstanceState.getString(MATH_OPERATIONS_KEY)
        binding.resultText.text = savedInstanceState.getString(RESULT_KEY)
    }

}