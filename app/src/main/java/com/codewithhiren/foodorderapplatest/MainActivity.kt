package com.codewithhiren.foodorderapplatest

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.codewithhiren.foodorderapplatest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.d("chauhan","Android developer")
        setSupportActionBar(binding.toolbar)

        navController = (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        appBarConfiguration = AppBarConfiguration(setOf(R.id.foodListFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (navController.currentDestination?.id == R.id.foodListFragment) {
                    this@MainActivity.finish()
                    return
                }
                navController.popBackStack(R.id.foodListFragment, false)
            }
        })
        window.statusBarColor = getColor(R.color.app_color)

        lifecycleScope.launch {
            navController.currentBackStack.collect { it: List<NavBackStackEntry> ->
                val stringBuilder = StringBuilder()
                it.forEach {
                    stringBuilder.append("${it.destination.label}  ")
                }
                Log.d("chauhan", stringBuilder.toString())
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.popBackStack(R.id.foodListFragment, false)
        return navController.navigateUp(appBarConfiguration) and super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (navController.currentBackStackEntry?.destination?.id == R.id.orderedFoodListFragment)
            return false
        if (navController.currentBackStackEntry?.destination?.id != R.id.foodListFragment)
            navController.popBackStack(R.id.foodListFragment, false)

        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

}
