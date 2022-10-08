package edu.virginia.cs4720.bucketlist.pss9fp.Room

import androidx.room.*
import edu.virginia.cs4720.bucketlist.pss9fp.Models.BucketItem

@Dao
interface BucketItemDao {

    /**
     * SELECT -> This retrieve rows from a table in a database
     * FROM -> You specify the table to retrieve the rows from
     * ORDER BY -> This is just a sort algorithm
     * ASC -> Ascending order
     * WHERE -> This is a condition used to query data
     * */
    @Query("SELECT*FROM BucketItem ORDER BY itemDueDate")
    fun getBucketItemList(): List<BucketItem>

    @Query("SELECT*FROM BucketItem WHERE itemId=:id")
    fun getItem(id: Int): BucketItem

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveItem(item: BucketItem)

    @Update
    fun updateItem(item: BucketItem)

    @Delete
    fun removeItem(item: BucketItem)
}