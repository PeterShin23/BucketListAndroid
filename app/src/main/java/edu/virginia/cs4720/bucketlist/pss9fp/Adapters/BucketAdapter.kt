package edu.virginia.cs4720.bucketlist.pss9fp.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import edu.virginia.cs4720.bucketlist.pss9fp.Models.BucketItem
import edu.virginia.cs4720.bucketlist.pss9fp.R

class BucketAdapter(var bucketList: List<BucketItem>? = ArrayList<BucketItem>()): RecyclerView.Adapter<BucketAdapter.ItemViewHolder>(){

    private var onItemClickedListener: OnItemClickedListener?= null

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
        holder.onBindViews(position)
    }

    inner class ItemViewHolder(val view: View, val bucketList: List<BucketItem>): RecyclerView.ViewHolder(view){
        fun onBindViews(position: Int){
            if (itemCount != 0){
                itemView.findViewById<TextView>(R.id.item_name).text = bucketList.get(position).itemName

                val dateText = bucketList.get(position).itemDueDate
                val month:String = if (dateText.get(4) == '0') dateText.substring(5,6) else dateText.substring(4,6)
                val day: String = if (dateText.get(6) == '0') dateText.substring(7,8) else dateText.substring(6,8)
                val formattedDate = "$month/$day"
                itemView.findViewById<TextView>(R.id.item_due_date).text = formattedDate

//                view.findViewById<TextView>(R.id.title).text = bucketList.get(position).title
//                view.findViewById<TextView>(R.id.first_letter).text = bucketList.get(position).title.first().toUpperCase().toString()
//                view.findViewById<ImageView>(R.id.priority_imgView).setImageResource(getImage(bucketList.get(position).priority))
            }
        }
//        private fun getImage(priority: Int): Int
//                = if (priority == 1) R.drawable.low_priority else if(priority == 2) R.drawable.medium_priority else R.drawable.high_priority
    }

    fun setItemClickedListener(onItemClickedListener: OnItemClickedListener){
        this.onItemClickedListener = onItemClickedListener
    }

    interface OnItemClickedListener{
        fun onItemClicked(item: BucketItem)
    }
}