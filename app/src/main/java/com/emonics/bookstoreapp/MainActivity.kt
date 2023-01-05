package com.emonics.bookstoreapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.emonics.bookstoreapp.fragments.HomeFragment
import com.emonics.bookstoreapp.fragments.SearchFragment
import com.emonics.bookstoreapp.fragments.ShelfFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val searchFragment = SearchFragment()
    private val homeFragment = HomeFragment()
    private val shelfFragment = ShelfFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(homeFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_search -> replaceFragment(searchFragment)
                R.id.ic_home -> replaceFragment(homeFragment)
                R.id.ic_shelf -> replaceFragment(shelfFragment)
            }
            true
        }


        /*val searchBtn = findViewById<Button>(R.id.search_btn)
        val homeBtn = findViewById<Button>(R.id.home_btn)
        val shelfBtn = findViewById<Button>(R.id.shelf_btn)

        searchBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchPage::class.java)
            startActivity(intent)
        }
        homeBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, HomePage::class.java)
            startActivity(intent)
        }
        shelfBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, ShelfPage::class.java)
            startActivity(intent)
        }*/
    }

    private fun replaceFragment(fragment: Fragment){
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}