package com.example.genshinwish

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.example.genshinwish.fragments.GanyuFragment
import com.example.genshinwish.fragments.HutaoFragment
import com.example.genshinwish.fragments.VentiFragment
import com.example.genshinwish.fragments.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpTabs()
    }

    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(VentiFragment(),"Venti")
        adapter.addFragment(HutaoFragment(),"Hutao")
        adapter.addFragment(GanyuFragment(),"Ganyu")
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = adapter
        val tabs = findViewById<TabLayout>(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}