package com.example.genshinwish.fragments.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.genshinwish.fragments.HomeFragment
import com.example.genshinwish.fragments.MusicFragment
import com.example.genshinwish.fragments.PublicFragment

class ViewPagerAdapter(supportFragmentManager: FragmentManager): FragmentStatePagerAdapter(supportFragmentManager,
    FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()

    override fun getCount(): Int {
        return 3
        //return mFragmentList.size
    }

    override fun getItem(position: Int): Fragment {
//        return when(position){
//            0->HomeFragment.newInstance()
//            1->MusicFragment.newInstance()
//            2->PublicFragment.newInstance()
//            else->HomeFragment.newInstance()
//        }
        return mFragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }
    fun addFragment(fragment: Fragment,title: String){
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }
}