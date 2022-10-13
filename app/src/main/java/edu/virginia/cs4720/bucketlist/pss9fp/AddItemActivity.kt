package edu.virginia.cs4720.bucketlist.pss9fp

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import edu.virginia.cs4720.bucketlist.pss9fp.Models.BucketItem
import edu.virginia.cs4720.bucketlist.pss9fp.Room.BucketItemDatabase
import java.util.*

class AddItemActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private val calendar = Calendar.getInstance()
    private var db: BucketItemDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Add Item"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        db = BucketItemDatabase.getInstance(this)

        val itemName = findViewById<EditText>(R.id.itemName)

        val itemDueDate = findViewById<TextView>(R.id.itemDueDate)
        itemDueDate.setOnClickListener {
            DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {

            // get values as they are at point of press
            val name = itemName.text.toString()
            val dueDate = itemDueDate.text.toString()
            Log.i("check name and duedate", "$name, $dueDate")

            // check if user input is valid
            if (name.equals("") || dueDate.equals("Press to Set Due Date")) {

                Toast.makeText(this, "Set an item and a due date!", Toast.LENGTH_SHORT).show()

            } else {
                // create item and save it to room
                val dueDateFormatted= convertStringForRoom(findViewById<TextView>(R.id.itemDueDate).text as String)
                val newItem = BucketItem(name, dueDateFormatted, false, "Not Finished Yet!")
                db!!.getBucketItemDao().saveItem(newItem)  // !! throw NPE, ? return null
                // go back to main
                finish()
            }

        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.i("Calendar", "$year-${month+1}-$dayOfMonth")

        findViewById<TextView>(R.id.itemDueDate).text = "Due Date: ${month+1}/$dayOfMonth/$year"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun convertStringForRoom(dateText: String): String {
        val date = dateText.substring(10).split("/")  // 10 accounts for "due date: "
        val month:String = if (date[0].length == 2) date[0] else "0" + date[0]
        val day:String = if (date[1].length == 2) date[1] else "0" + date[1]
        val year:String = date[2];

        val formattedDate = "$year$month$day"
        return formattedDate
    }
}

/***************************************************************************************
 *  REFERENCES
 *  Title: Android Date Picker Dialog
 *  Author: The Anroid Factory
 *  Date: 10/13/2022
 *  URL: https://www.youtube.com/watch?v=339-fxTcrmI&ab_channel=TheAndroidFactory
 *  Software License: N/A
 *  How I used: Inspiration for how to use date picker on a text view
 *
 ***************************************************************************************/