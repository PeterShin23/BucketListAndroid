package edu.virginia.cs4720.bucketlist.pss9fp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import java.util.*

class AddTaskActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val itemName = findViewById<TextView>(R.id.itemName)
        val itemDescription = findViewById<TextView>(R.id.itemDescription)

        val itemDueDate = findViewById<TextView>(R.id.itemDueDate).setOnClickListener {
            DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Log.i("check", "itemName: $itemName; itemDescription: $itemDescription; itemDueDate: $itemDueDate")
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.i("Calendar", "$year-${month+1}-$dayOfMonth")
        findViewById<TextView>(R.id.itemDueDate).text = "due date: ${month+1}/$dayOfMonth/$year"
    }
}