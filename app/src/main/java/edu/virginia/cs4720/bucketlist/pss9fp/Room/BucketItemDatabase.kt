package edu.virginia.cs4720.bucketlist.pss9fp.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.virginia.cs4720.bucketlist.pss9fp.Room.BucketItem

@Database(entities = [BucketItem::class], version = 1, exportSchema = false)
abstract class ItemListDatabase: RoomDatabase(){

    /**
     * This is an abstract method that returns a dao for the Db
     * */
    abstract fun getBucketItemDao(): BucketItemDao

    /**
     * A singleton design pattern is used to ensure that the database instance created is one
     * */
    companion object {
        val databaseName = "itemdatabase"
        var itemListDatabase: ItemListDatabase? = null

        fun getInstance(context: Context): ItemListDatabase?{
            if (itemListDatabase == null){
                itemListDatabase = Room.databaseBuilder(context,
                    ItemListDatabase::class.java,
                    ItemListDatabase.databaseName)
                    .allowMainThreadQueries()//i will remove this later, database are not supposed to be called on main thread
                    .build()
            }
            return itemListDatabase
        }
    }
}