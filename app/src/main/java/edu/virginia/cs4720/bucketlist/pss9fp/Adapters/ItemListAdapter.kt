package edu.virginia.cs4720.bucketlist.pss9fp.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.virginia.cs4720.bucketlist.pss9fp.Models.BucketItem
import edu.virginia.cs4720.bucketlist.pss9fp.R

class ItemListAdapter(private val itemList: List<BucketItem>): RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>() {

    // Holds the views for adding it to fields
    class ItemViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewName: TextView = itemView.findViewById(R.id.item_name)
        val textViewDueDate: TextView = itemView.findViewById(R.id.item_due_date)
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // inflates the card view design
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bucket_item_card, parent, false)
        return ItemViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val ItemsViewModel = itemList[position]

        // set name
        holder.textViewName.text = ItemsViewModel.itemName

        // set due date
        val dateText = ItemsViewModel.itemDueDate
        Log.i("date", dateText)
        val month:String = if (dateText.get(4) == '0') dateText.substring(5,6) else dateText.substring(4,6)
        val day: String = if (dateText.get(6) == '0') dateText.substring(7,8) else dateText.substring(6,8)
        val formattedDate = "$month/$day"
        holder.textViewDueDate.text = formattedDate

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return itemList.size
    }

}