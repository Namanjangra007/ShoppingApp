package com.example.shoppingapp2


import android.content.ClipData
import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.example.shoppingapp2.ui.home.HomeFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*


class Navigation_Activity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
        lateinit var products:List<ProductInfo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.shoppingapp2.R.layout.activity_navigation_)
        // calling Fragmentmanager ....................................................................

        val frag: FragmentManager = supportFragmentManager
        frag.beginTransaction().replace(com.example.shoppingapp2.R.id.navigationhome, HomeFragment())
            .commit()

        val toolbar: Toolbar = findViewById(com.example.shoppingapp2.R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(com.example.shoppingapp2.R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = findViewById(com.example.shoppingapp2.R.id.drawer_layout)
        val navView: NavigationView = findViewById(com.example.shoppingapp2.R.id.nav_view)
        val navController = findNavController(com.example.shoppingapp2.R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                com.example.shoppingapp2.R.id.nav_home,
                com.example.shoppingapp2.R.id.nav_products,
                com.example.shoppingapp2.R.id.nav_bestdeals,
                R.id.order_history,
                R.id.nav_account,
                R.id.sign_out
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(com.example.shoppingapp2.R.menu.navigation_, menu)
        val item:MenuItem = menu.findItem(R.id.search_bar)
        val item2:MenuItem = menu.findItem(R.id.notification)

        val search:SearchView = item.actionView as SearchView

        search.setOnQueryTextListener(object :SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(com.example.shoppingapp2.R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}