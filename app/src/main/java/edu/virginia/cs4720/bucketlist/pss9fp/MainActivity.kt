package edu.virginia.cs4720.bucketlist.pss9fp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.virginia.cs4720.bucketlist.pss9fp.Adapters.BucketAdapter
import edu.virginia.cs4720.bucketlist.pss9fp.Models.BucketItem
import edu.virginia.cs4720.bucketlist.pss9fp.Room.BucketItemDatabase


class MainActivity : AppCompatActivity(), BucketAdapter.OnItemClickedListener {

    private var db: BucketItemDatabase? = null
    private var adapter: BucketAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Bucket List")

        db = BucketItemDatabase.getInstance(this)
//        for (item in db!!.getBucketItemDao().getBucketItemList()) {
//            println(item.toString())
//        }
        adapter = BucketAdapter()
        adapter?.setItemClickedListener(this)

        val createItemButton = findViewById<FloatingActionButton>(R.id.addItemButton)
        createItemButton.setOnClickListener {
            startActivity(Intent(this, AddItemActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        adapter?.bucketList=db?.getBucketItemDao()?.getBucketItemList()
        val rv = findViewById<RecyclerView>(R.id.itemsRecyclerView)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
        rv.hasFixedSize()
    }

    override fun onItemClicked(item: BucketItem) {
        val intent = Intent(this, ItemInfoActivity::class.java)
        intent.putExtra("itemId", item.itemId)
        startActivity(intent)
    }

}