package edu.virginia.cs4720.bucketlist.pss9fp.Models

import android.icu.util.Calendar
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BucketItem")
data class BucketItem(
    @PrimaryKey(autoGenerate=true) var itemId:Int = 0,
    @ColumnInfo(name = "itemName") var itemName:String = "",
    @ColumnInfo(name = "itemDescription") var itemDescription:String = "",
    @ColumnInfo(name = "itemDueDate") var itemDueDate:String = "",
    @ColumnInfo(name = "itemStatus") var itemStatus:Boolean = false,
    @ColumnInfo(name = "itemCompletedDate") var itemCompletedDate:String? = ""
) {
}
