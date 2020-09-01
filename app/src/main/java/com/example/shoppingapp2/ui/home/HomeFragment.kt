package com.example.shoppingapp2.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.shoppingapp2.*
import com.google.android.material.tabs.TabLayout
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.horizontal_recyclerview.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import java.io.IOException
import java.util.*

class HomeFragment: Fragment(),ProductClickListner {
    lateinit var adapter1: Viewadapter
    lateinit var mPager : ViewPager
    var currentPage:Int =0
    lateinit var Time: Timer
    var delay_MS:Long = 2000
    var Period_MS:Long = 2000
    private lateinit var homeViewModel: HomeViewModel
    private var layoutManager: RecyclerView.LayoutManager? = null
    lateinit var kotlinModels: KotlinModels
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       homeViewModel =
        ViewModelProviders.of(this).get(HomeViewModel::class.java)

        // initializing adapter of viewadapter.............
        adapter1 = Viewadapter(requireActivity())

        val view:View = inflater.inflate(R.layout.fragment_home, container, false)
        view.recyclerview_main.layoutManager = LinearLayoutManager(activity)
        kotlinModels = KotlinModels()

        // Calling fetchjson!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        fetchjson()

        return view
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewpager = view?.findViewById<ViewPager>(R.id.viewPagernew)
        val tablayout = view?.findViewById<TabLayout>(R.id.tabLayout1)
        tablayout!!.addTab(tablayout.newTab().setText("Featured"))
        tablayout.addTab(tablayout.newTab().setText("On Sale"))
        tablayout.addTab(tablayout.newTab().setText("Top Rated"))
        tablayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter =
            activity?.supportFragmentManager?.let {
                Tab_Adapter(requireContext(),
                    it, tablayout.tabCount)


            }
        viewpager?.adapter = adapter
        viewpager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))
        tablayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewpager!!.currentItem = tab?.position!!
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        mPager= view?.findViewById<ViewPager>(com.example.shoppingapp2.R.id.viewpager)!!

        updatePager()
        imageSliderImplementation()
        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                currentPage = position
            }
        })
        mPager.setOnTouchListener {
                view, motionEvent ->
            stoppager()
            if (motionEvent.action == MotionEvent.ACTION_UP){
                imageSliderImplementation()
                updatePager()
            }
            return@setOnTouchListener false
        }
    }

    private fun imageSliderImplementation() {
        val adapter = Viewadapter(requireActivity())
        view?.viewpager?.adapter = adapter
    }
    fun updatePager(){
        val handler=android.os.Handler()
        val Update:Runnable = kotlinx.coroutines.Runnable {
            if(currentPage == adapter1.images.size) {
                currentPage = 0
            }
            mPager.setCurrentItem(currentPage++, true)
        }
        Time = Timer()
        Time.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, delay_MS, Period_MS)
    }
    fun stoppager(){
        Time.cancel()
    }
    // Fetching json for rest api!!!!!!!!!!!!!!!!!!!!!!!!

fun fetchjson(){

    val Url = kotlinModels.Url+"/api/productapi/v1/products"

    val client= OkHttpClient()
    val request = okhttp3.Request.Builder().url(Url).build()

    client.newCall(request).enqueue(object : Callback {

        override fun onFailure(call: Call, e: IOException) {
            println("failed to get the request")
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: okhttp3.Response) {
            val Body = response.body?.string()

            val GSON = GsonBuilder().create()
            val productdetail: List<ProductInfo> = GSON.fromJson(Body,Array<ProductInfo>::class.java).toList()
               activity?.runOnUiThread{
                view?.recyclerview_main?.adapter = MainAdapter(productdetail,this@HomeFragment)
                Log.d("Adapter","$productdetail")
            }
        }
    })
}
    override fun onClick(view: ProductInfo, position: Int) {

        //Toast.makeText(this, view.product_name,Toast.LENGTH_SHORT).show()
        val intent = Intent(activity, ProductDesc::class.java)
        intent.putExtra(CustomViewHolder.productTag, view.product_name)
        intent.putExtra(CustomViewHolder.productPriceTag, view.price)
        intent.putExtra(CustomViewHolder.productImgTag, view.image)
        intent.putExtra(CustomViewHolder.productCategory, view.catagory)
        intent.putExtra(CustomViewHolder.productSubcategory, view.subcatagory)
        intent.putExtra(CustomViewHolder.productDesc, view.desc)
        intent.putExtra(CustomViewHolder.productQty, view.quantity)
        startActivity(intent)
    }


}