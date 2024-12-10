package com.example.elquetedelagana

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerDay: Spinner
    private lateinit var spinnerMonth: Spinner
    private lateinit var spinnerYear: Spinner
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerDay = findViewById(R.id.spinnerDay)
        spinnerMonth = findViewById(R.id.spinnerMonth)
        spinnerYear = findViewById(R.id.spinnerYear)
        val btnCalculate: Button = findViewById(R.id.btnCalculate)
        tvResult = findViewById(R.id.tvResult)

        // Set up spinners
        setupSpinner(spinnerDay, 1, 31)
        setupSpinner(spinnerMonth, 1, 12)
        setupSpinner(spinnerYear, 1900, Calendar.getInstance().get(Calendar.YEAR))

        btnCalculate.setOnClickListener {
            if (validateDate()) {
                calculateAge()
            } else {
                Toast.makeText(this, "Introdueix una data vàlida.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupSpinner(spinner: Spinner, start: Int, end: Int) {
        val numbers = (start..end).toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, numbers)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun validateDate(): Boolean {
        val day = spinnerDay.selectedItem.toString().toInt()
        val month = spinnerMonth.selectedItem.toString().toInt()
        val year = spinnerYear.selectedItem.toString().toInt()

        val calendar = Calendar.getInstance().apply {
            set(year, month - 1, day)
        }

        return calendar.get(Calendar.MONTH) == month - 1 && calendar.get(Calendar.DAY_OF_MONTH) == day
    }

    private fun calculateAge() {
        val day = spinnerDay.selectedItem.toString().toInt()
        val month = spinnerMonth.selectedItem.toString().toInt()
        val year = spinnerYear.selectedItem.toString().toInt()

        val dob = Calendar.getInstance().apply {
            set(year, month - 1, day)
        }
        val today = Calendar.getInstance()

        var years = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
        var months = today.get(Calendar.MONTH) - dob.get(Calendar.MONTH)
        var days = today.get(Calendar.DAY_OF_MONTH) - dob.get(Calendar.DAY_OF_MONTH)

        if (months < 0) {
            years--
            months += 12
        }
        if (days < 0) {
            months--
            days += today.getActualMaximum(Calendar.DAY_OF_MONTH)
        }

        tvResult.text = getString(R.string.resultat, years, months, days)
    }
}