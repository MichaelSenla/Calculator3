package com.example.calculator3

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calculator3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_RESULT = "RESULT"
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

        loadDataFromSharedPreferences()
        binding.calculatorButton.setOnClickListener {
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
        saveButtonListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val result = data?.getStringExtra(EXTRA_RESULT)
            binding.currentValueResult.text = result
        }
    }

    private fun loadDataFromSharedPreferences() {
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val savedResult = sharedPreferences.getString(EXTRA_RESULT, null)
        binding.savedValueText.text = savedResult
    }

    private fun saveButtonListener() {
        binding.saveButton.setOnClickListener {
            sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
            sharedPreferences.edit().apply {
                putString(EXTRA_RESULT, appendNewValue(binding.currentValueResult.text.toString()))
            }.apply()
        }
    }

    private fun appendNewValue(anotherResult: String?): String =
        StringBuilder(binding.savedValueText.text.toString()).append(LINE_SEPARATOR)
            .append(anotherResult).toString()
}