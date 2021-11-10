package com.example.calculator3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator3.databinding.ActivityCalculatorBinding
import kotlinx.android.synthetic.main.activity_calculator.*
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorActivity : AppCompatActivity() {

    companion object {
        private const val RESULT_KEY = "RESULT_KEY"
        private const val MATH_OPERATIONS_KEY = "MATH_OPERATIONS_KEY"
        private const val EXTRA_RESULT = "RESULT"
        private const val DIVISION_BY_ZERO = "/0"
    }

    private lateinit var binding: ActivityCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
        setOkayListener()
    }

    private fun setListeners() {
        binding.buttonZero.setOnClickListener {
            inputValue("0")
        }
        binding.buttonOne.setOnClickListener {
            inputValue("1")
        }
        binding.buttonTwo.setOnClickListener {
            inputValue("2")
        }
        binding.buttonThree.setOnClickListener {
            inputValue("3")
        }
        binding.buttonFour.setOnClickListener {
            inputValue("4")
        }
        binding.buttonFive.setOnClickListener {
            inputValue("5")
        }
        binding.buttonSix.setOnClickListener {
            inputValue("6")
        }
        binding.buttonSeven.setOnClickListener {
            inputValue("7")
        }
        binding.buttonEight.setOnClickListener {
            inputValue("8")
        }
        binding.buttonNine.setOnClickListener {
            inputValue("9")
        }
        binding.subtraction.setOnClickListener {
            inputValue("-")
        }
        binding.addition.setOnClickListener {
            inputValue("+")
        }
        binding.division.setOnClickListener {
            inputValue("/")
        }
        binding.multiplication.setOnClickListener {
            inputValue("*")
        }
        binding.clearButton.setOnClickListener {
            binding.mathOperations.text = ""
            binding.resultText.text = ""
        }
        binding.equality.setOnClickListener {
            if (binding.mathOperations.text.contains(DIVISION_BY_ZERO)) {
                binding.mathOperations.text = getString(R.string.divisionByZero)
            } else {
                settingUpMathLibrary()
                checkingIfTheNumberIsDouble(settingUpMathLibrary())
            }
        }
    }

    private fun setOkayListener() {
        binding.okayButton.setOnClickListener {
            setResult(RESULT_OK, intent.apply {
                putExtra(EXTRA_RESULT, binding.resultText.text)
            })
            finish()
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

    private fun inputValue(str: String) {
        if (resultText.text.isNotEmpty()) {
            binding.mathOperations.text = binding.resultText.text
            binding.resultText.text = ""
        }
        binding.mathOperations.append(str)
    }

    private fun settingUpMathLibrary(): Double {
        val expression = ExpressionBuilder(binding.mathOperations.text.toString()).build()

        return expression.evaluate()
    }

    private fun checkingIfTheNumberIsDouble(result: Double) {
        val longResult = result.toLong()
        if (result == longResult.toDouble()) {
            binding.resultText.text = longResult.toString()
        } else {
            binding.resultText.text = result.toString()
        }
    }
}