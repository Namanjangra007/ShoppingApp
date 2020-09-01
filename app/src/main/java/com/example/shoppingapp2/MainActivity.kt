package com.example.shoppingapp2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login_layout.*
import kotlinx.android.synthetic.main.register_layout.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    lateinit var kotlinModels:KotlinModels
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    //THESE VARIABLES FOR LOGIN
        val etEmail2 = findViewById<EditText>(R.id.etEmail2)
        val etpassword2 = findViewById<EditText>(R.id.etpassword2)
        val btlogin = findViewById<Button>(R.id.btnlogin)
        //THESE VARIABLES FOR REGISTER
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etpassword = findViewById<EditText>(R.id.etpassword)
        val etconfirmpassword = findViewById<EditText>(R.id.etconfirm)
        val etmobileNo =findViewById<EditText>(R.id.etmobile)
        val etusername = findViewById<EditText>(R.id.etusername)
        val btnregister = findViewById<Button>(R.id.btnregister)


        btnregister.setOnClickListener {
            //checking registration parmas :)
            if(etusername.text.toString().isEmpty() &&etEmail.text.toString().isEmpty()&& etpassword.text.toString().isEmpty() && etconfirmpassword.text.toString().isEmpty() && etmobileNo.text.toString().isEmpty()){
                Toast.makeText(this,"Please fill the all detail first",Toast.LENGTH_SHORT).show()
            }
            else{
                //Checking password is remembered or not
                if (etpassword.text.toString() ==etconfirmpassword.text.toString()) {
                    Toast.makeText(this, "Confirm password is same as Password", Toast.LENGTH_SHORT)
                        .show()
                    Toast.makeText(this, "Registration successful !", Toast.LENGTH_SHORT).show()
                    //  Register user....................here,,,,,,
                    registeruser()
                    showLoginlayout()

                }else{
                    Toast.makeText(this,"Confirm password is not same as Password",Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnlogin.setOnClickListener {
            if(etEmail2.text.toString().isEmpty()&& etpassword2.text.toString().isEmpty()){
                Toast.makeText(this,"Please fill the all detail first",Toast.LENGTH_SHORT).show()
            }
            else{
                // Login ............................................................Here!!.............................
               // loginuser()

            }
            startActivity(Intent(this,Navigation_Activity::class.java))
        }
        rbLogin.setOnClickListener {
            showLoginlayout()
        }
        rbRegister.setOnClickListener {
            showRegisterlayout()
        }
    }
    fun registeruser(){
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        val url = kotlinModels.Url+"/api/erpapi/v1/user/register"
        val jobj = JSONObject()

        jobj.put("username", etusername.text.toString().trim())
        jobj.put("email", etEmail.text.toString().trim())
        jobj.put("password", etpassword.text.toString().trim())
        Log.d("json object", "$jobj")
        val postRequest: JsonObjectRequest =
            JsonObjectRequest(Request.Method.POST, url, jobj,
                { response -> // response
                    val param = jobj
                    Toast.makeText(this, "Success $response", Toast.LENGTH_SHORT).show()
                    Log.d("Response", "$response")
                },
                { // error
                        error ->
                    error.printStackTrace()
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    Log.d("myapp", "$error")
                })
        postRequest.retryPolicy = DefaultRetryPolicy(
            6000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        // Adding request to request queue
        requestQueue.add(postRequest)

    }
    fun loginuser(){
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
         kotlinModels = KotlinModels()
        val url =   kotlinModels.Url+"/api/erpapi/v1/user/login"
        val jobj = JSONObject()

        jobj.put("username", etEmail2.text.toString().trim())
        jobj.put("password", etpassword2.text.toString().trim())
        Log.d("json object", "$jobj")
        val postRequest: JsonObjectRequest =
            JsonObjectRequest(Request.Method.POST, url, jobj,
                { response -> // response
                    Toast.makeText(this, "Success Welcome! $response", Toast.LENGTH_SHORT).show()

                    Log.d("Response", "$response")
                },
                { // error
                        error ->
                    error.printStackTrace()
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    Log.d("myapp", "$error")
                })
        postRequest.retryPolicy = DefaultRetryPolicy(
            6000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        // Adding request to request queue
        requestQueue.add(postRequest)

    }

    fun showLoginlayout(){
        include.visibility = View.VISIBLE
        include2.visibility = View.GONE
    }
    fun showRegisterlayout(){
        include2.visibility = View.VISIBLE
        include.visibility = View.GONE
    }
}