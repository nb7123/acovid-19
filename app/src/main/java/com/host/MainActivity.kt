package com.host

import android.app.Service
import android.content.ContentProvider
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = findNavController(R.id.fragment_container_view)
        NavigationUI.setupWithNavController(bottom_navigation_view, navController)

//        Service
        resources
    }
}
