package com.example.shoppingapp2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_layout.view.*
import kotlinx.android.synthetic.main.sizeofproducts.view.*
import java.util.*
import kotlin.collections.ArrayList


class MainAdapter(productdetail: List<ProductInfo>,var clickListner: ProductClickListner) :RecyclerView.Adapter<CustomViewHolder>(),Filterable {

        val product = productdetail
    var productforfilter = product


    override fun getItemCount(): Int {

       return product.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

//         holder.itemView.txtName.text = product[position].product_name
//            holder.itemView.txtPrice.text = product[position].price.toString()
//
//        val image1 = holder.itemView.imgPic
//        Picasso.with(holder.itemView.context).load("http://192.168.0.106:8000"+product[position].image).into(image1)
//        Log.d("image", product[position].image)
//        holder.productname = product[position]
        holder.initialize(product[position],clickListner)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

      val layoutinflater = LayoutInflater.from(parent.context)
        val cellforrow = layoutinflater.inflate(R.layout.product_layout,parent,false)
        return CustomViewHolder(cellforrow)
    }

    override fun getFilter(): Filter {
        return filter1
    }
    val filter1 = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filterdlist = ArrayList<String>()
            if (p0.toString().isEmpty())
            {
                filterdlist.addAll(listOf(productforfilter.toString()))
            }
            else{

                for ( prodsearch2 in productforfilter) {
                    if (prodsearch2.toString().toLowerCase(Locale.ROOT).contains(p0.toString().toLowerCase(Locale.ROOT))) {

                        filterdlist.add(prodsearch2.toString())
                    }
                }

            }
            val filterResult:FilterResults = FilterResults()
            filterResult.values = filterdlist
            return filterResult
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
        productforfilter = p1?.values as List<ProductInfo>
            notifyDataSetChanged()
        }

    }

}
class CustomViewHolder(itemView: View, var productname: ProductInfo? = null) : RecyclerView.ViewHolder(itemView) {
lateinit var kotlinModels:KotlinModels
    var txtname = itemView.txtName
    var txtprice = itemView.txtPrice
    var img = itemView.imgPic

            companion object{
                val productTag = "Product Tag"
                val productPriceTag = "product price tag"
                val productImgTag = "product Image tag"
                val productCategory = "product category"
                val productSubcategory = "product subcategory"
                val productDesc = "product desc"
                val productQty = "product Quantity"

            }
    fun initialize(product:ProductInfo, action:ProductClickListner){
    kotlinModels = KotlinModels()
        txtname.text = product.product_name
        txtprice.text = product.price.toString()
        Picasso.with(itemView.context).load(kotlinModels.Url+product.image).into(img)
        itemView.setOnClickListener { action.onClick(product,adapterPosition) }
    }

}



