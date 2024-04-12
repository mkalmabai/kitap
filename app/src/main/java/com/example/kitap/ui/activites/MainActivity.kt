package com.example.kitap.ui.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.kitap.R
import com.example.kitap.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainFragmentsHolder) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = binding.bottomMenu
        bottomNavigationView.background =null
        val addProductButton = binding.addProduct

        val bottomAppBar = binding.bottomAppBar

        bottomNavigationView.setupWithNavController(navController)
        binding.addProduct.setOnClickListener {
            navController.navigate(R.id.addProductFragment)
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment,R.id.chatFragment,R.id.favoriteFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    bottomAppBar.visibility = View.VISIBLE
                    addProductButton.visibility = View.VISIBLE
                }
                else -> {
                    bottomNavigationView.visibility = View.GONE
                    bottomAppBar.visibility = View.GONE
                    addProductButton.visibility = View.GONE
                }
            }
        }
    }
}