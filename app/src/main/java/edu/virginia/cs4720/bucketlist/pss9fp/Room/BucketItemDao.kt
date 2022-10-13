package edu.virginia.cs4720.bucketlist.pss9fp.Room

import androidx.room.*
import edu.virginia.cs4720.bucketlist.pss9fp.Models.BucketItem

@Dao
interface BucketItemDao {

    @Query("SELECT*FROM BucketItem ORDER BY itemStatus, itemCompletedDate, itemDueDate")
    fun getBucketItemList(): List<BucketItem>

    @Query("SELECT*FROM BucketItem WHERE itemId=:id")
    fun getItem(id: Int): BucketItem

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveItem(item: BucketItem)

    @Update
    fun updateItem(item: BucketItem)

    @Delete
    fun removeItem(item: BucketItem)

    @Query("DELETE FROM BucketItem")
    fun deleteAll()
}