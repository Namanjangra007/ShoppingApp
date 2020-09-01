package com.example.shoppingapp2

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class Gif_Class: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState )
        setContentView(R.layout.landingpage);
        val prefs:SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        val alreadyExecuted:Boolean = prefs.getBoolean("alreadyExecuted",true)
        if (alreadyExecuted)
        {
            requestPermission()

        }
        else{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
}

    //HERE IS AUTO LOCATION FUNCTIONALITY
    private fun hasExternalStoragePermission()=
        ActivityCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun hasForgroundLocationPermission()=
        ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun hasBackgoundLocationPermission()=
        ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED


    // HERE WE TAKE PERMISSION
    private fun requestPermission():Boolean{
        var PermissionToRequest = mutableListOf<String>()
        if (!hasExternalStoragePermission()){
            PermissionToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!hasForgroundLocationPermission()){
            PermissionToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (!hasBackgoundLocationPermission()){
            PermissionToRequest.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
        if (PermissionToRequest.isNotEmpty()){
            ActivityCompat.requestPermissions(this,PermissionToRequest.toTypedArray(),0)
        }
    return false
    }
//WHEN WE HAVE GRANTED PERMISSION
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults.isNotEmpty()){
            for (i in grantResults.indices){
                if (grantResults[i]== PackageManager.PERMISSION_GRANTED){
                    Log.d("PermissionRequest","${permissions[i]} Granted")
                    println("yes true bro!")
                    var handler = Handler();
                    handler.postDelayed({println("handler2 post delay works!!")
                        kotlin.run {
                            println("going in!! after a second2")

                                val intent = Intent(this, MainActivity::class.java);
                                startActivity(intent);
                                finish();
                            val prefs:SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
                            val edit: SharedPreferences.Editor = prefs.edit()
                            edit.putBoolean("alreadyExecuted",false)
                            edit.apply()

                        }
                    }, 1000);

                }

            }

        }
    }
}