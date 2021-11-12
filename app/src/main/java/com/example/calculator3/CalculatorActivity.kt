package com.example.calculator3

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator3.databinding.ActivityCalculatorBinding

class CalculatorActivity : AppCompatActivity() {

    companion object {
        private const val RESULT_KEY = "RESULT_KEY"
        private const val MATH_OPERATIONS_KEY = "MATH_OPERATIONS_KEY"
        private const val DIVISION_BY_ZERO = "/0"
    }

    private lateinit var binding: ActivityCalculatorBinding
    private var canAddOperation = false
    private val calculation = Calculation()

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
                putExtra(MainActivity.EXTRA_RESULT, binding.resultText.text)
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
            if (calculation.separateInputLine(binding.mathOperations.text.toString()).isEmpty()) {
                binding.resultText.text = ""
            }
            val timesDivision =
                calculation.finishCalculationTimesDivision(calculation.separateInputLine(binding.mathOperations.text.toString()))
            if (timesDivision.isEmpty()) {
                binding.resultText.text = ""
            }
            binding.resultText.text = calculation.calculatePlusMinus(timesDivision).toString()
        }
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