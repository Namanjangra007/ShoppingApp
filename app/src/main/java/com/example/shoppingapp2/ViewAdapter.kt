package com.example.shoppingapp2

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager


class Viewadapter(private val context: Context): PagerAdapter() {
    lateinit var kotlinModels:KotlinModels
    val layoutInflater: LayoutInflater? = null

    var images = arrayOf(R.drawable.ic_menu_camera,R.drawable.ic_menu_gallery,R.drawable.ic_menu_slideshow)

    override fun isViewFromObject(view: View, `object`: Any): Boolean {

        return view === `object`
    }

    override fun getCount(): Int {
        return images.size
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater.inflate(R.layout.custom_layout,null)
        val image = v.findViewById<View>(R.id.imageView_slide) as ImageView
        image.setImageResource(images[position])

        val vp = container as ViewPager
        vp.addView(v,0)

        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val v = `object` as View
        vp.removeView(v)
    }



}