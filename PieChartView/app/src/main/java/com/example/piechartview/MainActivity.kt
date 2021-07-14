package com.example.piechartview

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : Activity() {

    private lateinit var text: TextView
    private lateinit var text2: TextView
    private lateinit var pieChartView: PieChartView
    private lateinit var editText: EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text = findViewById(R.id.text)
        text2 = findViewById(R.id.text2)
        pieChartView = findViewById(R.id.pie)
        editText = findViewById(R.id.editText)
        button = findViewById(R.id.button)

        button.isEnabled = false

        editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                text2.visibility = View.VISIBLE
                pieChartView.visibility = View.GONE

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                text2.visibility = View.GONE
                pieChartView.visibility = View.VISIBLE

            }

            override fun afterTextChanged(s: Editable?) {
                button.isEnabled = true
                if(s.isNullOrEmpty()) {
                    button.isEnabled = false
                }
            }

        })

        button.setOnClickListener() {
            try {
                val list: List<Int> = editText.text.toString().split(", ").map { it.toInt() }
                pieChartView.sendData(list)
            } catch (e: Exception) {
                text.text = e.message
            }
        }


    }
}