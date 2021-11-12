package com.example.calculator3

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_RESULT = "RESULT"
        private const val LINE_SEPARATOR = "\n"
        private const val SHARED_PREFERENCES = "SHARED_PREFERENCES"
        private const val REQUEST_CODE = 1
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        loadDataFromSharedPreferences()
        saveButtonListener()
        binding.calculatorButton.setOnClickListener {
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
        binding.savedValueText.movementMethod = ScrollingMovementMethod()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            binding.currentValueResult.text = data?.getStringExtra(EXTRA_RESULT)
        }
    }

    private fun loadDataFromSharedPreferences() {
        binding.savedValueText.text = sharedPreferences.getString(EXTRA_RESULT, null)
    }

    private fun saveButtonListener() {
        binding.saveButton.setOnClickListener {
            sharedPreferences.edit().apply {
                putString(EXTRA_RESULT, appendNewValue(binding.currentValueResult.text.toString()))
            }.apply()
            binding.savedValueText.text = appendNewValue(binding.currentValueResult.text.toString())
        }
    }

    private fun appendNewValue(anotherResult: String?): String =
        StringBuilder(binding.savedValueText.text.toString()).append(LINE_SEPARATOR)
            .append(anotherResult).toString()
}