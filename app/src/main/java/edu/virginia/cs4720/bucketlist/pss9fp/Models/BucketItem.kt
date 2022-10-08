package edu.virginia.cs4720.bucketlist.pss9fp.Models

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.virginia.cs4720.bucketlist.pss9fp.R

@Entity(tableName = "BucketItem")
data class BucketItem(
    @ColumnInfo(name = "itemName") var itemName:String = "",
    @ColumnInfo(name = "itemDueDate") var itemDueDate:String = "",
    @ColumnInfo(name = "itemStatus") var itemStatus:Boolean = false,
    @ColumnInfo(name = "itemCompletedDate") var itemCompletedDate:String? = "Not Finished Yet!",
    @PrimaryKey(autoGenerate=true) var itemId:Int = 0
) {

    fun isCompleted() = itemStatus
    fun checkbox(): Int = if (isCompleted()) R.drawable.ic_baseline_check_box_24
        else R.drawable.ic_baseline_check_box_outline_blank_24
    fun text(context: Context): Int = if (isCompleted()) ContextCompat.getColor(context, com.google.android.material.R.color.material_blue_grey_800)
        else ContextCompat.getColor(context, R.color.black)

    override fun toString(): String {
        return "BucketItem(itemName='$itemName', itemDueDate='$itemDueDate', itemStatus=$itemStatus, itemCompletedDate=$itemCompletedDate, itemId=$itemId)"
    }

}

