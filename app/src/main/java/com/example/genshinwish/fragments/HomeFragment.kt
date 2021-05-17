package com.example.genshinwish.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.genshinwish.R
import com.example.genshinwish.controller.Wish
import com.example.genshinwish.databinding.FragmentHomeBinding
import com.example.genshinwish.fragments.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout


class HomeFragment : Fragment() {

    companion object{
        fun newInstance(): HomeFragment{
            return HomeFragment()
        }
    }
    private lateinit var binding : FragmentHomeBinding
    private var wish: Wish = Wish()
    private var totalWish: Int = 0
    private var totalMoney: Int = 0
    private var animation: Boolean = true
    private var listResult = ArrayList<Int>()
    private var listResultTemp = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        //setUpTabs()
        binding.btnReset.setOnClickListener {
            //test dialog waiting
            val progressDialog = ProgressDialog(context)
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
//
        binding.btnTenWish.setOnClickListener {
            totalWish += 10
            totalMoney += 1600
            val listTemp: ArrayList<Int> = wish.wishX10()
            listResult.addAll(listTemp)
            listResultTemp.addAll(listTemp)
            updateResult()
        }
        return binding.root
    }

    private fun updateResult() {
        listResultTemp.clear()
        binding.tvCounterWish.text = totalWish.toString()
        binding.tvCounterMoney.text = totalMoney.toString()
        var listSort = listResult
        listSort.sortDescending() //sap xep giam dan 5-4-3
        //tra ve list count item
        var listItem = countItem(listResult)

        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_list_item_1,
            listItem
        )
        binding.listView.adapter = arrayAdapter
        binding.listView.visibility = View.VISIBLE

        //animation
//        if (animation == true) {
//            val dialog = MaterialDialog(requireContext())
//                .noAutoDismiss()
//                .customView(R.layout.dialog_result)
//            dialog.show()
//
//
//        Handler().postDelayed({dialog.dismiss()},5000)
//        //dialog setting
//            val dialog2 = MaterialDialog(requireContext())
//                .noAutoDismiss()
//                .customView(R.layout.dialog_setting)
//            dialog2.show()
//        }


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
        binding.listView.visibility = View.GONE
        binding.imvVentii.visibility = View.GONE
        wish.resetValue()         //reset cac gia tri random
    }

//    private fun setUpTabs() {
//        val adapter = ViewPagerAdapter(supportFragmentManager)
//        adapter.addFragment(VentiFragment(), "Venti")
//        adapter.addFragment(HutaoFragment(), "Hutao")
//        adapter.addFragment(GanyuFragment(), "Ganyu")
//        val viewPager = findViewById<ViewPager>(R.id.viewPager)
//        viewPager.adapter = adapter
//        val tabs = findViewById<TabLayout>(R.id.tabs)
//        tabs.setupWithViewPager(viewPager)
//    }
}