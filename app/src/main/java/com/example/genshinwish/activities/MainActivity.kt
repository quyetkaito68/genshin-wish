package com.example.genshinwish.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.genshinwish.R
import com.example.genshinwish.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
        //Bottom Navigation
        val navController = findNavController(R.id.hostFragment)
        nav_bottom.setupWithNavController(navController)

        //Drawer Navigation
        NavigationUI.setupWithNavController(navigation_view,navController)
    }
}