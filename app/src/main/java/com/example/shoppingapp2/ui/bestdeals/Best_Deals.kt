package com.example.shoppingapp2.ui.bestdeals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.shoppingapp2.R

class Best_Deals : Fragment() {

    private lateinit var bestdeals: Best_DealsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bestdeals =
            ViewModelProviders.of(this).get(Best_DealsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_bestdeals, container, false)
        val textView: TextView = root.findViewById(R.id.text_bestdeals)
        bestdeals.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}