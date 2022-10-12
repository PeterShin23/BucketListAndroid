package edu.virginia.cs4720.bucketlist.pss9fp

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import edu.virginia.cs4720.bucketlist.pss9fp.Models.BucketItem
import edu.virginia.cs4720.bucketlist.pss9fp.Room.BucketItemDatabase
import java.util.*

class ItemInfoActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    lateinit var name: TextView
    lateinit var dueDate: TextView
    lateinit var completionDate: TextView

    private val calendar = Calendar.getInstance()
    private var db: BucketItemDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_info)
        setTitle("Edit Item")
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Edit Item"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        // get db
        db = BucketItemDatabase.getInstance(this)


        // set information in appropriate fields
        val currItem = db!!.getBucketItemDao().getItem(intent.getIntExtra("itemId", 0))

        name = findViewById<EditText>(R.id.editName)
        name.text = currItem.itemName

        dueDate = findViewById<TextView>(R.id.editDueDate)
        dueDate.setOnClickListener {
            DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        val dueDateTemp = currItem.itemDueDate
        dueDate.text = "Due Date: ${dueDateTemp?.let { convertRoomToString(it) }}"

        val status = currItem.isCompleted()
        completionDate = findViewById<TextView>(R.id.editCompletionDate)
        val completionDateTemp = currItem.itemCompletedDate
        if (status) {
            completionDate.text =
                "Completed Date: ${completionDateTemp?.let { convertRoomToString(it) }}"
        } else {
            completionDate.text = "Not Finished Yet!"
        }
        Log.i("checking date", completionDate.text.toString())

        // make updates
        val updateButton = findViewById<Button>(R.id.updateButton)
        updateButton.setOnClickListener {

            // get values
            val id = intent.getIntExtra("itemId", 0)
            val name = name.text.toString()
            val dueDate: String = convertStringForRoom(findViewById<TextView>(R.id.editDueDate).text as String, 10)
            Log.i("check", "itemName: $name; itemDueDate: $dueDate")
            val completionDate: String = if (status) convertStringForRoom(findViewById<TextView>(R.id.editCompletionDate).text as String, 16) else "Not Finished Yet!"

            // store in room
            val updatedItem = BucketItem(name, dueDate, status, completionDate, id)
            Log.i("item content", "${updatedItem.itemId}, ${updatedItem.itemName}")
            db!!.getBucketItemDao().updateItem(updatedItem)
            Log.i("updated? ", "${db?.getBucketItemDao()?.getItem(updatedItem.itemId)}")
            finish()
        }


    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.i("Calendar", "$year-${month+1}-$dayOfMonth")

        dueDate = findViewById<TextView>(R.id.editDueDate)
        dueDate.text = "due date: ${month+1}/$dayOfMonth/$year"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun convertRoomToString(date: String): String {
        val year = date.substring(0, 4)
        val month = if (date.substring(4,5) == "0") date.substring(5,6) else date.substring(4, 6)
        val day = if (date.substring(6,7) == "0") date.substring(7, 8) else date.substring(6, 8)

        val formattedDate = "$month/$day/$year"
        return formattedDate
    }

    fun convertStringForRoom(dateText: String, index: Int): String {
        val date = dateText.substring(index).split("/")  // 10 accounts for "due date: "
        val month:String = if (date[0].length == 2) date[0] else "0" + date[0]
        val day:String = if (date[1].length == 2) date[1] else "0" + date[1]
        val year:String = date[2];

        val formattedDate = "$year$month$day"
        return formattedDate
    }
}