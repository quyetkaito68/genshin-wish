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
import com.example.genshinwish.model.Character
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var wish: Wish = Wish()
    private var totalWish: Int = 0
    private var totalMoney: Int = 0
    private var animation: Boolean = true
    private var listResult = ArrayList<Int>()
    private var listResultTemp = ArrayList<Int>()
    private var newListResult = ArrayList<Character>()
    private var newListResultTemp = ArrayList<Character>()

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
            //wish int
//            val res: Int = wish.wishX1()
//            listResult.add(res)
//            listResultTemp.add(res)
//            if (res==5){
//                binding.imvVentii.setImageResource(R.drawable.venti)
//                binding.imvVentii.visibility = View.VISIBLE
//            }
            //wish object
            val item: Character = wish.wishX1Char()
            newListResult.add(item)
            newListResultTemp.add(item)
            //show Image
            showImageList(newListResultTemp)
            //
            updateResult()
        }

        binding.btnTenWish.setOnClickListener {
            totalWish += 10
            totalMoney += 1600
            //wish int
//            val listTemp: ArrayList<Int> = wish.wishX10()
//            listResult.addAll(listTemp)
//            listResultTemp.addAll(listTemp)

            //wish object
            val listTemp: ArrayList<Character> = wish.wishX10Char()
            newListResult.addAll(listTemp)
            newListResultTemp.addAll(listTemp)
            //
            showImageList(newListResultTemp)
            //
            updateResult()
        }
        binding.videoAnimation.setOnClickListener {
            it.visibility = View.GONE
        }
    }

    private fun showImageList(lists: ArrayList<Character>) {
        binding.imvVentii.visibility = View.VISIBLE
        showImage(lists[0])
    }

    private fun showImage(item: Character) {
        var mId = item.getCharId()
        var drawable = when (mId) {
            "venti" -> R.drawable.venti
            "razor" -> R.drawable.razor
            "amber" -> R.drawable.amber
            "diona" -> R.drawable.diona
            "lisa" -> R.drawable.lisa
            "kaeya" -> R.drawable.kaeya
            "fischl" -> R.drawable.fischl
            "xinyan" -> R.drawable.xinyan
            "xingqiu" -> R.drawable.xingqiu
            "chongyun" -> R.drawable.chongyun
            "succrose" -> R.drawable.succrose
            "ningguang" -> R.drawable.ningguang
            "beidou" -> R.drawable.beidou
            "noelle" -> R.drawable.noelle
            "barbara" -> R.drawable.barbara
            "bennett" -> R.drawable.bennett
            "xiangling" -> R.drawable.xiangling
            else -> R.drawable.weapon_thrilling_tales_of_dragon_slayers
        }
        binding.imvVentii.setImageResource(drawable)


    }

    private fun updateResult() {
        //animation optional
        if (animation == true) {
            //playAnimation(listResultTemp) //lua chon file mp4(3-4-5 sao)
            playAnimation(newListResultTemp)
        } else {
            binding.videoAnimation.visibility = View.GONE
        }
        //listResultTemp.clear()
        newListResultTemp.clear()
        //~end animation

        //update counter
        binding.tvCounterWish.text = totalWish.toString()
        binding.tvCounterMoney.text = totalMoney.toString()
        //update list result
        //hien thi list item <String>
        var listItem = countItem(newListResult)
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listItem
        )
        binding.listView.adapter = arrayAdapter
        binding.listView.isVisible = true
        //~end hien thi listItem

    }

    private fun playAnimation(list: ArrayList<Character>) {

        var uriPath: String = "android.resource://" + packageName.toString() + "/"
        var uri: Uri = Uri.parse(uriPath + R.raw.three_star_wish)
        for (i in list) {
            if (i.getCstar() == 5) {
                uri = Uri.parse(uriPath + R.raw.five_star_wish)
                break
            } else if (i.getCstar() == 4) {
                uri = Uri.parse(uriPath + R.raw.four_star_wish)
            }
        }
        binding.videoAnimation.setVideoURI(uri)
        binding.videoAnimation.visibility = View.VISIBLE
        binding.videoAnimation.start()


    }

    private fun countItem(listSort: ArrayList<Character>): ArrayList<String> {
        var list = ArrayList<Int>()
        var listString = ArrayList<String>()
        var threeStar: Int = 0
        var fourStar: Int = 0
        var fiveStar: Int = 0
        var name: String = ""
        for (i in listSort) {
            if (i.getCstar() == 3) threeStar++
            if (i.getCstar() == 4) fourStar++
            if (i.getCstar() == 5) {
                fiveStar++
                name = i.getCharId()
            }
        }
        list.add(threeStar)
        list.add(fourStar)
        list.add(fiveStar)
        list.sortDescending()
        listString.add("5 sao: " + list[2].toString() + " " + name)
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
        newListResult.clear()
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