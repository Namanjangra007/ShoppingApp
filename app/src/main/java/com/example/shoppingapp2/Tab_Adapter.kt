package com.example.shoppingapp2

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.shoppingapp2.tablayoutUI.FeaturedFragment
import com.example.shoppingapp2.tablayoutUI.OnSale
import com.example.shoppingapp2.tablayoutUI.TopRated

class Tab_Adapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int): FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> {
                return FeaturedFragment()
            }
            1 -> {
                return OnSale()
            }
            2 -> {
                return TopRated()
            }

        }
        throw IllegalStateException("position $position is invalid for this viewpager")
    }
}