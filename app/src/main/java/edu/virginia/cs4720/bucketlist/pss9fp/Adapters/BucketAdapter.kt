package edu.virginia.cs4720.bucketlist.pss9fp.Adapters

import android.app.usage.NetworkStats
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import edu.virginia.cs4720.bucketlist.pss9fp.MainActivity
import edu.virginia.cs4720.bucketlist.pss9fp.Models.BucketItem
import edu.virginia.cs4720.bucketlist.pss9fp.R
import edu.virginia.cs4720.bucketlist.pss9fp.Room.BucketItemDatabase
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class BucketAdapter(var bucketList: List<BucketItem>? = ArrayList<BucketItem>()): RecyclerView.Adapter<BucketAdapter.ItemViewHolder>(){

    private var onItemClickedListener: OnItemClickedListener?= null
//    private var onCheckClickedListener: OnCheckClickedListener?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layout = R.layout.bucket_item_card
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ItemViewHolder(view, bucketList!!)
    }

    override fun getItemCount(): Int {
        return if(bucketList!!.isEmpty()) 0 else bucketList!!.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int){
        holder.view.setOnClickListener{onItemClickedListener!!.onItemClicked(bucketList!!.get(position))}
//        holder.view.setOnClickListener{onCheckClickedListener!!.onCheckClicked(bucketList!!.get(position))}
        holder.onBindViews(position)
    }

    inner class ItemViewHolder(val view: View, val bucketList: List<BucketItem>): RecyclerView.ViewHolder(view){

        fun onBindViews(position: Int){

            val db = BucketItemDatabase.getInstance(view.context)

            if (itemCount != 0){
                itemView.findViewById<TextView>(R.id.item_name).text = bucketList.get(position).itemName

                val dateText = bucketList.get(position).itemDueDate
                val month:String = if (dateText.get(4) == '0') dateText.substring(5,6) else dateText.substring(4,6)
                val day: String = if (dateText.get(6) == '0') dateText.substring(7,8) else dateText.substring(6,8)
                val formattedDate = "$month/$day"
                itemView.findViewById<TextView>(R.id.item_due_date).text = formattedDate


                val currItem = bucketList.get(position)
                val checkbox = itemView.findViewById<ImageButton>(R.id.itemCheckButton)
                checkbox.setImageResource(currItem.checkbox())
                checkbox.setOnClickListener {
                    currItem.itemStatus = !currItem.itemStatus
                    if (currItem.isCompleted()) {
                        // get current time
                        val date = Calendar.getInstance().time
                        val formatter = SimpleDateFormat("yyyyMMdd")
                        val currTime = formatter.format(date)

                        val completedItem = BucketItem(currItem.itemName, currItem.itemDueDate, true, currTime, currItem.itemId)
                        db!!.getBucketItemDao().updateItem(completedItem)
//                        for (item in db.getBucketItemDao().getBucketItemList()) {
//                            println(item.toString())
//                        }
                    }
                    else if (!currItem.isCompleted()) {
                        val uncompletedItem = BucketItem(currItem.itemName, currItem.itemDueDate,false,"Not Finished Yet!",currItem.itemId)
                        db!!.getBucketItemDao().updateItem(uncompletedItem)
                        Log.i(
                            "updated? ",
                            "${db.getBucketItemDao().getItem(uncompletedItem.itemId)}"
                        )
                    }
                    checkbox.setImageResource(currItem.checkbox())
                }



//                // If checked: strike through, set status as true, find curr time and mark completed date, update item in database
//                // If unchecked: remove strike through, set status as false, replace completed date with text "Not Finished Yet!"
//                val currItem = bucketList.get(position)
//                val checkbox = itemView.findViewById<CheckBox>(R.id.itemCheckButton)
//                checkbox.setOnClickListener {
//                    if (checkbox.isChecked) {
//                        // get current time
//                        val date = Calendar.getInstance().time
//                        val formatter = SimpleDateFormat("yyyyMMdd")
//                        val currTime = formatter.format(date)
//
//                        val completedItem = BucketItem(currItem.itemName, currItem.itemDueDate, true, currTime, currItem.itemId)
//                        db!!.getBucketItemDao().updateItem(completedItem)
//                        for (item in db.getBucketItemDao().getBucketItemList()) {
//                            println(item.toString())
//                        }
//                    } else {
//                        val uncompletedItem = BucketItem(
//                            currItem.itemName,
//                            currItem.itemDueDate,
//                            false,
//                            "Not Finished Yet!",
//                            currItem.itemId
//                        )
//                        db!!.getBucketItemDao().updateItem(uncompletedItem)
//                        Log.i(
//                            "updated? ",
//                            "${db.getBucketItemDao().getItem(uncompletedItem.itemId)}"
//                        )
//                    }
//
//                }
            }
        }
//        private fun getImage(priority: Int): Int
//                = if (priority == 1) R.drawable.low_priority else if(priority == 2) R.drawable.medium_priority else R.drawable.high_priority
    }

    fun setItemClickedListener(onItemClickedListener: OnItemClickedListener){
        this.onItemClickedListener = onItemClickedListener
    }

//    fun setCheckClickedListener(onCheckClickedListener: OnCheckClickedListener) {
//        this.onCheckClickedListener = onCheckClickedListener
//    }

    interface OnItemClickedListener{
        fun onItemClicked(item: BucketItem)
    }

//    interface OnCheckClickedListener{
//        fun onCheckClicked(item: BucketItem)
//    }
}