package com.example.shoppingapp2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_desc.*
import kotlinx.android.synthetic.main.sizeofproducts.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException


class ProductDesc :AppCompatActivity() {
        lateinit var kotlinModels: KotlinModels
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.shoppingapp2.R.layout.product_desc)


       val imgDesc = product_desc_img
        val imgpp = pp
            kotlinModels = KotlinModels()

        val navBarTitle = intent.getStringExtra(CustomViewHolder.productTag)
        supportActionBar?.title = navBarTitle
        txtName2.text = intent.getStringExtra(CustomViewHolder.productTag)
        txtPrice2.text = intent.getIntExtra(CustomViewHolder.productPriceTag,-1).toString()
        txtcategory.text = intent.getStringExtra(CustomViewHolder.productCategory)
        txtsubcategory.text = intent.getStringExtra(CustomViewHolder.productSubcategory)
        desc_id.text = intent.getStringExtra(CustomViewHolder.productDesc)
        val quantity:Int = intent.getIntExtra(CustomViewHolder.productQty,-1)

        Picasso.with(this).load(kotlinModels.Url+intent.getStringExtra(CustomViewHolder.productImgTag)).into(imgDesc)
        Picasso.with(this).load(kotlinModels.Url+intent.getStringExtra(CustomViewHolder.productImgTag)).into(imgpp)
        productsizeapi()

      //  spinner()
        quantity()
        if (quantity == 0)
            {
                outofstock.visibility = View.VISIBLE
            }
        else{
                instock.visibility = View.VISIBLE
            }

    }

    fun productsizeapi(){
        val subcatagory = findViewById<TextView>(R.id.txtsubcategory)
        val sizebtn = findViewById<Button>(R.id.sizebutton)
        sizebtn.setOnClickListener {

            visiblityofsize()
            val Url = kotlinModels.Url+"/api/productapi/v1/products/catagory"
           val MEDIA_TYPE: MediaType? = "application/json".toMediaTypeOrNull()
            val client= OkHttpClient()
            val objcat = JSONObject()
            objcat.put("subcatagory",subcatagory.text.toString())
            val catagorybody:RequestBody =RequestBody.create(MEDIA_TYPE,objcat.toString())
            val request = okhttp3.Request.Builder().post(catagorybody).url(Url).build()

            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    println("failed to get the request")
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: okhttp3.Response) {
                    val Body = response.body?.string()
                    Log.d("Body","$Body")
                    val GSON = GsonBuilder().create()
                    val productcatagory: List<productcatagoryinfo> = GSON.fromJson(Body,Array<productcatagoryinfo>::class.java).toList()
                    Log.d("pruductcategory","${productcatagory.toList()}")
                    runOnUiThread {
                        
                        Toast.makeText(this@ProductDesc, "Success ${productcatagory.toList()}", Toast.LENGTH_SHORT).show()
                    }

                }
            })

        }

    }
    fun visiblityofsize(){
        include3size.visibility = View.VISIBLE

    }
// For quantity of product
    fun quantity() {

        val add = findViewById<Button>(R.id.addqty)
        var addingplus =1
        var subtracting = 1
        val plusbtn = findViewById<Button>(R.id.plusqty)
        val minusbtn = findViewById<Button>(R.id.minusqty)
        var newPrice: Int = intent.getIntExtra(CustomViewHolder.productPriceTag, -1)
        val textint = findViewById<EditText>(R.id.quantity2)

        plusbtn.setOnClickListener {
            if (textint.text.toString().isEmpty()||textint.text.toString().isNotEmpty()){
                 addingplus +=textint.text.toString().toInt()
            }
            else{
                Toast.makeText(this,"something did not match",Toast.LENGTH_SHORT).show()
            }
        }

        minusbtn.setOnClickListener {
            if (textint.text.toString().isNotEmpty()){
                subtracting -= textint.text.toString().toInt()
            }
            else{
                Toast.makeText(this,"cannot subtract because field is empty",Toast.LENGTH_SHORT).show()
            }
        }

        add.setOnClickListener {
            if (textint.text.toString().isEmpty()){
                Toast.makeText(this,"Please add the quantity",Toast.LENGTH_SHORT).show()
            }
            else
            {
                newPrice *= textint?.text.toString().toInt()
                estimatePrice.text = newPrice.toString()
            }

        }

    }


//    fun spinner(){
//        val languages = resources.getStringArray(R.array.quantity_arrays)
//        // access the spinner
//        val spinner = findViewById<Spinner>(R.id.spinner1)
//        if (spinner != null) {
//            val adapter = ArrayAdapter(this,
//                android.R.layout.simple_spinner_item, languages)
//            spinner.adapter = adapter
//
//            spinner.onItemSelectedListener = object :
//                AdapterView.OnItemSelectedListener {
//
//                override fun onNothingSelected(parent: AdapterView<*>) {
//                    // write code to perform some action
//                }
//                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                    var newPrice = intent.getIntExtra(CustomViewHolder.productPriceTag,-1)
//                    newPrice *= languages[p2!!].toInt()
//                    estimatePrice.text = newPrice.toString()
//                }
//            }
//        }
//    }

}