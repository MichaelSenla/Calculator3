package com.example.calculator3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator3.databinding.ActivityCalculatorBinding
import kotlinx.android.synthetic.main.activity_calculator.*
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorActivity : AppCompatActivity() {

    companion object {
        private const val RESULT_KEY = "RESULT_KEY"
        private const val MATH_OPERATIONS_KEY = "MATH_OPERATIONS_KEY"
        private const val RESULT = "RESULT"
    }

    private lateinit var binding: ActivityCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()

        setOkayButton()
    }

    private fun setListeners() {
        binding.buttonZero.setOnClickListener {
            setTextFields("0")
        }
        binding.buttonOne.setOnClickListener {
            setTextFields("1")
        }
        binding.buttonTwo.setOnClickListener {
            setTextFields("2")
        }
        binding.buttonThree.setOnClickListener {
            setTextFields("3")
        }
        binding.buttonFour.setOnClickListener {
            setTextFields("4")
        }
        binding.buttonFive.setOnClickListener {
            setTextFields("5")
        }
        binding.buttonSix.setOnClickListener {
            setTextFields("6")
        }
        binding.buttonSeven.setOnClickListener {
            setTextFields("7")
        }
        binding.buttonEight.setOnClickListener {
            setTextFields("8")
        }
        binding.buttonNine.setOnClickListener {
            setTextFields("9")
        }
        binding.subtraction.setOnClickListener {
            setTextFields("-")
        }
        binding.addition.setOnClickListener {
            setTextFields("+")
        }
        binding.division.setOnClickListener {
            setTextFields("/")
        }
        binding.multiplication.setOnClickListener {
            setTextFields("*")
        }
        binding.clearButton.setOnClickListener {
            binding.mathOperations.text = ""
            binding.resultText.text = ""
        }
        binding.equality.setOnClickListener {
            if (binding.mathOperations.text.contains("/0")) {
                binding.mathOperations.text = getString(R.string.divisionByZero)
            } else {
                val expression = ExpressionBuilder(binding.mathOperations.text.toString()).build()
                val result = expression.evaluate()
                val longResult = result.toLong()
                if (result == longResult.toDouble()) {
                    binding.resultText.text = longResult.toString()
                } else {
                    binding.resultText.text = result.toString()
                }
            }
        }
    }

    private fun setOkayButton() {
        binding.okayButton.setOnClickListener {
            val currentNumber = binding.resultText.text
            val intent = Intent()
            intent.putExtra(RESULT, currentNumber)
            setResult(1, intent)
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val mathOperations = binding.mathOperations.text.toString()
        val result = binding.resultText.text.toString()
        outState.putString(MATH_OPERATIONS_KEY, mathOperations)
        outState.putString(RESULT_KEY, result)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val mathOperations = savedInstanceState.getString(MATH_OPERATIONS_KEY)
        val result = savedInstanceState.getString(RESULT_KEY)
        binding.mathOperations.text = mathOperations
        binding.resultText.text = result
    }

    private fun setTextFields(str: String) {
        if (resultText.text.isNotEmpty()) {
            binding.mathOperations.text = binding.resultText.text
            binding.resultText.text = ""
        }
        binding.mathOperations.append(str)
    }
}