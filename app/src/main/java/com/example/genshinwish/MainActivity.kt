package com.example.genshinwish

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.example.genshinwish.databinding.ActivityMainBinding
import com.example.genshinwish.fragments.GanyuFragment
import com.example.genshinwish.fragments.HutaoFragment
import com.example.genshinwish.fragments.VentiFragment
import com.example.genshinwish.fragments.adapters.ViewPagerAdapter
import com.example.genshinwish.controller.Wish
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var wish: Wish = Wish()
    private var totalWish: Int = 0
    private var totalMoney: Int = 0
    private var listResult = ArrayList<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setUpTabs()

        binding.btnReset.setOnClickListener {
            //test dialog waiting
            val progressDialog = ProgressDialog(this)
            progressDialog.show()
            Handler().postDelayed({ progressDialog.dismiss() }, 500)
            //reset ve 0
            resetResult()
        }

        binding.btnSingleWish.setOnClickListener {
            listResult.add(wish.wishX1())
            totalWish++
            updateResult()
        }

        binding.btnTenWish.setOnClickListener {
            totalWish+=10
            listResult.addAll(wish.wishX10())
            updateResult()
        }


    }

    private fun updateResult() {
        binding.tvCounterWish.text = totalWish.toString()
        var listSort = listResult
        listSort.sortDescending() //sap xep giam dan 5-4-3
        val arrayAdapter: ArrayAdapter<Int> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listSort
        )
        binding.listView.adapter = arrayAdapter
        //money
        //sort 4-5 sao lên dau tien

    }

    private fun resetResult() {
        totalWish =0
        totalMoney =0
        binding.tvCounterWish.text = totalWish.toString() //de mot bien luu total wish->toString
        binding.tvCounterMoney.text = totalMoney.toString() //mot bien luu tien ->toString
        listResult.clear()        //listView reset
        wish.resetValue()         //reset cac gia tri random
    }

    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(VentiFragment(), "Venti")
        adapter.addFragment(HutaoFragment(), "Hutao")
        adapter.addFragment(GanyuFragment(), "Ganyu")
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = adapter
        val tabs = findViewById<TabLayout>(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}