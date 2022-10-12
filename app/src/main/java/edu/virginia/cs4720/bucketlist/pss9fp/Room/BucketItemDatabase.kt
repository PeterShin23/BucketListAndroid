package edu.virginia.cs4720.bucketlist.pss9fp.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.virginia.cs4720.bucketlist.pss9fp.Models.BucketItem

@Database(entities = [BucketItem::class], version = 10, exportSchema = true)
abstract class BucketItemDatabase: RoomDatabase(){

    /**
     * This is an abstract method that returns a dao for the Db
     * */
    abstract fun getBucketItemDao(): BucketItemDao

    /**
     * A singleton design pattern is used to ensure that the database instance created is one
     * */
    companion object {
        val databaseName = "itemdatabase"
        var itemListDatabase: BucketItemDatabase? = null

        fun getInstance(context: Context): BucketItemDatabase?{
            if (itemListDatabase == null){
                itemListDatabase = Room.databaseBuilder(context,
                    BucketItemDatabase::class.java,
                    BucketItemDatabase.databaseName)
                    .allowMainThreadQueries()//i will remove this later, database are not supposed to be called on main thread
                    .createFromAsset("databases/examples.db")
//                    .fallbackToDestructiveMigration() // needed this to resolve version 1 to version 2 migration of db
                    .build()
            }
            println(itemListDatabase!!.getBucketItemDao().getBucketItemList())
            return itemListDatabase
        }
    }
}