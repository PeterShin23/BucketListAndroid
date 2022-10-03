package edu.virginia.cs4720.bucketlist.pss9fp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.virginia.cs4720.bucketlist.pss9fp.Adapters.ItemListAdapter
import edu.virginia.cs4720.bucketlist.pss9fp.Models.BucketItem
import edu.virginia.cs4720.bucketlist.pss9fp.Room.ItemListDatabase


class MainActivity : AppCompatActivity() {

    private var itemdb: ItemListDatabase? = null
    private var adapter: ItemListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create make item button
        val createItemButton = findViewById<FloatingActionButton>(R.id.addItemButton)
        createItemButton.setOnClickListener{
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }

        // get item list
        itemdb = ItemListDatabase.getInstance(this)
        val allItems = itemdb!!.getBucketItemDao().getBucketItemList()

        // example item
//        itemdb!!.getBucketItemDao().saveItem(BucketItem(0, "Press the + to make item", "example desc", "due date: 0/0/0000"))

        // get recyclerview
        val rv = findViewById<RecyclerView>(R.id.itemsRecyclerView)
        rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ItemListAdapter(allItems)
        }

//        // get recyclerview
//        val main_recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
//
//        // get item list
//        itemdb = ItemListDatabase.getInstance(this)
//        val allItems = itemdb!!.getBucketItemDao().getBucketItemList()
//        adapter = ItemListAdapter(allItems)
//
//        // set adapter to recyclerview
//        val manager = LinearLayoutManager(this)
//        main_recyclerview.setLayoutManager(manager)
//        main_recyclerview.setAdapter(adapter)

    }



}