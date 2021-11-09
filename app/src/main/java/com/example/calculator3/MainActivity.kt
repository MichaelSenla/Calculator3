package com.example.calculator3

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calculator3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val RESULT = "RESULT"
        private const val LINE_SEPARATOR = "\n"
        private const val SHARED_PREFERENCES = "SHARED_PREFERENCES"
        private const val REQUEST_CODE = 1
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindingInit()
        loadDataFromSharedPreferences()
        binding.calculatorButton.setOnClickListener {
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
        saveButtonListener(null)
    }

    private fun bindingInit() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            val result = data?.getStringExtra(RESULT)
            binding.currentValueResult.text = result
            val lastResult = appendNewValue(result)
            saveButtonListener(lastResult)
        }
    }

    private fun loadDataFromSharedPreferences() {
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        var savedResult = sharedPreferences.getString(RESULT, null)
        savedResult = appendNewValue(savedResult)
        binding.firstSavedValueText.text = savedResult

    }

    private fun saveButtonListener(result: String?) {
        binding.saveButton.setOnClickListener {
            sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.apply {
                putString(RESULT, result)
            }.apply()
        }
    }

    private fun appendNewValue(anotherResult: String?): String {
        val result = binding.firstSavedValueText.text.toString()
        val lastResult = StringBuilder(result).append(LINE_SEPARATOR).append(anotherResult)

        return lastResult.toString()
    }
}