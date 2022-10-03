package edu.virginia.cs4720.bucketlist.pss9fp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import edu.virginia.cs4720.bucketlist.pss9fp.Models.BucketItem
import edu.virginia.cs4720.bucketlist.pss9fp.Room.ItemListDatabase
import java.util.*

class AddItemActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private val calendar = Calendar.getInstance()
    private var db: ItemListDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        db = ItemListDatabase.getInstance(this)

        val itemName = findViewById<EditText>(R.id.itemName)
        val itemDescription = findViewById<EditText>(R.id.itemDescription)

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

            // get values
            val name = itemName.text.toString()
            val desc = itemDescription.text.toString()
//            val dueDate:Int = convertStringForRoom(findViewById<TextView>(R.id.itemDueDate).text as String)
            val dueDate: String = convertStringForRoom(findViewById<TextView>(R.id.itemDueDate).text as String)
            Log.i("check", "itemName: $name; itemDescription: $desc; itemDueDate: $dueDate")

            // store in room
            val item = BucketItem(0,name, desc, dueDate)
            db!!.getBucketItemDao().saveItem(item)  // !! throw NPE, ? return null
            Log.i("msg", "going back to main")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.i("Calendar", "$year-${month+1}-$dayOfMonth")

        findViewById<TextView>(R.id.itemDueDate).text = "due date: ${month+1}/$dayOfMonth/$year"
    }

    fun convertStringForRoom(dateText: String): String {
        val date = dateText.substring(10).split("/")  // 10 accounts for "due date: "
        val month:String = if (date[0].length == 2) date[0] else "0" + date[0]
        val day:String = if (date[1].length == 2) date[1] else "0" + date[1]
        val year:String = date[2];

        val formattedDate = "$year$month$day"
//        val DateToInt: Int = formattedDate.toInt()
//        return DateToInt
        return formattedDate
    }
}