package com.example.ageinminute

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView? = null
    private var tvAgeInHour : TextView? = null
    private var tvAgeInMinute : TextView? = null
    private var tvAgeInSeconds : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btnDateSelector)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInHour = findViewById(R.id.tvAgeInHour)
        tvAgeInMinute = findViewById(R.id.tvAgeInMinute)
        tvAgeInSeconds = findViewById(R.id.tvAgeInSeconds)

        btnDatePicker.setOnClickListener {
            getDatePickerDialog()
        }
    }

    private fun getDatePickerDialog() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, {_, selectedYear, selectedMonth, selectedDayOfMonth ->
            Toast.makeText(this, "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear", Toast.LENGTH_SHORT).show()

            val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            tvSelectedDate?.text = selectedDate

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val theDate = sdf.parse(selectedDate)

            theDate?.let {
                val selectedDatedInMinute = theDate.time / 60000
                val currentTime = sdf.parse(sdf.format(System.currentTimeMillis()))?.time
                currentTime?.let {
                    val differenceInMinutes = (currentTime / 60000) - selectedDatedInMinute
                    tvAgeInMinute?.text = differenceInMinutes.toString()

                    val differenceInHour = differenceInMinutes / 60
                    tvAgeInHour?.text = differenceInHour.toString()

                    val differenceInSeconds = differenceInMinutes * 60
                    tvAgeInSeconds?.text = differenceInSeconds.toString()
                }
            }

        }, year,  month, day)

        dpd.datePicker.maxDate = System.currentTimeMillis() - 8640000
        dpd.show()
    }
}