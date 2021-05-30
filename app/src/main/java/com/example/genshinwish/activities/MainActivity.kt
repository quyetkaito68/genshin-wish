package com.example.genshinwish.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.genshinwish.R
import com.example.genshinwish.databinding.ActivityMainBinding
import com.example.genshinwish.fragments.HomeFragment
import com.example.genshinwish.fragments.MusicFragment
import com.example.genshinwish.fragments.PublicFragment
import com.example.genshinwish.fragments.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Bottom Navigation
//        val navController = findNavController(R.id.hostFragment)
//        nav_bottom.setupWithNavController(navController)
//
//       // Drawer Navigation
//        NavigationUI.setupWithNavController(navigation_view,navController)

//        //View Pager
        setUpTabs()
        setBottomNavigation()
        setViewPagerListener()

    }

    private fun setViewPagerListener() {
        viewPager?.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
                Log.e("quyetkaito","onPageScrolled")
            }

            override fun onPageSelected(position: Int) {
                Log.e("quyetkaito","onPageSelected")
                nav_bottom.menu.getItem(position).setChecked(true)
            }

            override fun onPageScrollStateChanged(state: Int) {
                Log.e("quyetkaito","onPageScrollStateChanged")
            }

        })
    }

    private fun setBottomNavigation() {
        nav_bottom.setOnNavigationItemSelectedListener {
            viewPager.currentItem = when(it.itemId){
                R.id.homeFragment -> 0
                R.id.musicFragment -> 1
                R.id.publicFragment -> 2
                else ->0
            }
            return@setOnNavigationItemSelectedListener true
        }
    }


    private fun setUpTabs() {
        val mAdapter = ViewPagerAdapter(supportFragmentManager)
        mAdapter.addFragment(HomeFragment(), "Home")
        mAdapter.addFragment(MusicFragment(), "Music")
        mAdapter.addFragment(PublicFragment(), "Entertainment")
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = mAdapter
//        val tabs = findViewById<TabLayout>(R.id.tabs)
//        tabs.setupWithViewPager(viewPager)
    }

}