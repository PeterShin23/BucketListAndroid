package edu.virginia.cs4720.bucketlist.pss9fp.Room

import androidx.room.*

@Dao
interface BucketItemDao {

    /**
     * SELECT -> This retrieve rows from a table in a database
     * FROM -> You specify the table to retrieve the rows from
     * ORDER BY -> This is just a sort algorithm
     * ASC -> Ascending order
     * WHERE -> This is a condition used to query data
     * */
    @Query("SELECT*FROM BucketItemList ORDER BY itemDueDate")
    fun getBucketItemList(): List<BucketItem>

    @Query("SELECT*FROM BucketItemList WHERE itemId=:id")
    fun getItem(id: Int): BucketItem
    /**
     * @param item is what we want to save in our database
     * so many conflict can occur when a data is to be saved, the strategy is used to handle such conflicts
     * Abort -> this aborts the transaction
     * Ignore -> this ignores and continues the transaction
     * Replace -> this replace the data
     * others includes fail, and roolback
     * */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveItem(item: BucketItem)

    @Update
    fun updateItem(item: BucketItem)

    @Delete
    fun removeItem(item: BucketItem)
}