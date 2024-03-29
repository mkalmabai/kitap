package com.example.kitap.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kitap.R
import com.example.kitap.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomMenu
        bottomNavigationView.background =null
    }
}