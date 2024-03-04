package com.msedith.wishlistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addItemButton: FloatingActionButton
    private var wishlistItems = mutableListOf<WishlistItem>()
    private val wishlistAdapter = WishlistAdapter(wishlistItems)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        addItemButton = findViewById(R.id.addItemButton)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = wishlistAdapter

        addItemButton.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.add_item_dialog, null)
            val dialog = AlertDialog.Builder(this)
                .setTitle("Add Wishlist Item")
                .setView(dialogView)
                .setPositiveButton("Add") { _, _ ->
                    // Retrieve the input and create a new WishlistItem
                    val name = dialogView.findViewById<EditText>(R.id.nameEditText).text.toString()
                    val price = dialogView.findViewById<EditText>(R.id.priceEditText).text.toString().toDoubleOrNull() ?: 0.0
                    val url = dialogView.findViewById<EditText>(R.id.urlEditText).text.toString()

                    val newItem = WishlistItem(name, price, url)
                    updateWishlist(newItem)
                }
                .setNegativeButton("Cancel", null)
                .create()
            dialog.show()
        }
    }

    private fun updateWishlist(newItem: WishlistItem) {
        wishlistItems.add(newItem)
        wishlistAdapter.notifyItemInserted(wishlistItems.size - 1)
    }
}