package com.example.shoppingapp2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreen: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashfile);
        println("Loading Screen to Main Activity")
        var handler = Handler();
            handler.postDelayed({println("handler post delay works!!")
                kotlin.run {
                    println("going in!! after 3 second")
                    val intent = Intent(this, Gif_Class::class.java);
                    startActivity(intent);
                    finish();
                }
            }, 1000);

        }
    }
