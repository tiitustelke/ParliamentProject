package com.example.oopproject1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.oopproject1.databinding.ActivityMainBinding
import com.example.oopproject1.fragments.list.ListFragmentDirections

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_view) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setOnNavigationItemSelectedListener { item ->

            when(item.itemId) {

                R.id.nav_home -> {
                    navController.navigate(R.id.fragment_home)
                }

                R.id.nav_parties -> {
                    navController.navigate(R.id.)
                }
            }
            true
        }
    }

}