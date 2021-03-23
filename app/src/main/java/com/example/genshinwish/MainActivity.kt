package com.example.genshinwish

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.VideoView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.genshinwish.databinding.ActivityMainBinding
import com.example.genshinwish.fragments.GanyuFragment
import com.example.genshinwish.fragments.HutaoFragment
import com.example.genshinwish.fragments.VentiFragment
import com.example.genshinwish.fragments.adapters.ViewPagerAdapter
import com.example.genshinwish.controller.Wish
import com.example.genshinwish.dialog.SettingDialog
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var wish: Wish = Wish()
    private var totalWish: Int = 0
    private var totalMoney: Int = 0
    private var animation: Boolean = true
    private var listResult = ArrayList<Int>()
    private var listResultTemp = ArrayList<Int>()


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

        binding.btnSetting.setOnClickListener {
            //hien thi dialog
            if (animation == true) {
                animation = false
            } else {
                animation = true
            }
//            val dialog: SettingDialog = SettingDialog()
//            dialog.show(supportFragmentManager, "ehe")
        }

        binding.btnSingleWish.setOnClickListener {
            totalWish++
            totalMoney += 160
            val res: Int = wish.wishX1()
            listResult.add(res)
            listResultTemp.add(res)
            if (res==5){
                binding.imvVentii.setImageResource(R.drawable.venti)
                binding.imvVentii.visibility = View.VISIBLE
            }
            updateResult()
        }

        binding.btnTenWish.setOnClickListener {
            totalWish += 10
            totalMoney += 1600
            val listTemp: ArrayList<Int> = wish.wishX10()
            listResult.addAll(listTemp)
            listResultTemp.addAll(listTemp)
            updateResult()
        }


    }

    private fun updateResult() {
//        val intent = Intent(this,SecondActivity::class.java)
//        startActivity(intent)
        if (animation==true){
            playAnimation(listResultTemp)
        }else{
            binding.videoAnimation.visibility = View.GONE
        }
        listResultTemp.clear()

        binding.tvCounterWish.text = totalWish.toString()
        binding.tvCounterMoney.text = totalMoney.toString()
        var listSort = listResult
        listSort.sortDescending() //sap xep giam dan 5-4-3
        //tra ve list count item
        var listItem = countItem(listResult)

        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listItem
        )
        binding.listView.adapter = arrayAdapter
        binding.listView.isVisible = true

        //animation
//        if (animation == true) {
//            val dialog = MaterialDialog(this)
//                .noAutoDismiss()
//                .customView(R.layout.dialog_result)
//            dialog.show()
        //

        //
        //Handler().postDelayed({dialog.dismiss()},5000)
        //dialog setting
//            val dialog2 = MaterialDialog(this)
//                .noAutoDismiss()
//                .customView(R.layout.dialog_setting)
//            dialog2.show()
//        }


    }

    private fun playAnimation(list: ArrayList<Int>) {
        var uriPath: String = "android.resource://" + packageName.toString() + "/"
        var uri: Uri = Uri.parse(uriPath + R.raw.three_star_wish)
        for (i in list) {
            if (i == 5) {
                uri = Uri.parse(uriPath + R.raw.five_star_wish)
                break
            } else if (i == 4) {
                uri = Uri.parse(uriPath + R.raw.four_star_wish)
            }
        }
        binding.videoAnimation.setVideoURI(uri)
        binding.videoAnimation.visibility = View.VISIBLE
        binding.videoAnimation.start()

    }

    private fun countItem(listSort: ArrayList<Int>): ArrayList<String> {
        var list = ArrayList<Int>()
        var listString = ArrayList<String>()
        var threeStar: Int = 0
        var fourStar: Int = 0
        var fiveStar: Int = 0
        for (i in listSort) {
            if (i == 3) threeStar++
            if (i == 4) fourStar++
            if (i == 5) fiveStar++
        }
        list.add(threeStar)
        list.add(fourStar)
        list.add(fiveStar)
        list.sortDescending()
        listString.add("5 sao: " + list[2].toString())
        listString.add("4 sao: " + list[1].toString())
        listString.add("3 sao: " + list[0].toString())
        return listString
    }

    private fun resetResult() {
        totalWish = 0
        totalMoney = 0
        binding.tvCounterWish.text = totalWish.toString() //de mot bien luu total wish->toString
        binding.tvCounterMoney.text = totalMoney.toString() //mot bien luu tien ->toString
        listResult.clear()        //listView reset
        binding.listView.isVisible = false
        binding.imvVentii.visibility = View.GONE
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