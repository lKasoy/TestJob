package com.example.testjob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.testjob.constants.Constants.ITEMS_FRAGMENT
import com.example.testjob.ui.fragments.ItemsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragment(savedInstanceState)
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add<ItemsFragment>(R.id.container, ITEMS_FRAGMENT)
                setReorderingAllowed(true)
            }
        }
    }
}