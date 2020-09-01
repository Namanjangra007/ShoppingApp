package com.example.shoppingapp2

import android.content.Context
import android.content.res.AssetManager
import java.io.IOException
import java.io.InputStream
import java.util.*


class util {
    @Throws(IOException::class)
    fun getProperty(key: String?, context: Context): String? {
        val properties = Properties()
        val assetManager: AssetManager = context.getAssets()
        val inputStream: InputStream = assetManager.open("config.properties")
        properties.load(inputStream)
        return properties.getProperty(key)
    }
}