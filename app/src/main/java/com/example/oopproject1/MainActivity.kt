package com.example.oopproject1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.oopproject1.data.MemberViewModel
import com.example.oopproject1.databinding.ActivityMainBinding

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * Class for the MainActivity. MainActivity holds the Fragment container and bottom menu and handles navigating between the fragments via the menu.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var memberViewModel: MemberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        memberViewModel = ViewModelProvider(this).get(MemberViewModel::class.java)

        val view = binding.root
        setContentView(view)

        //tells the worker to update the parliamentmembers
        memberViewModel.updateMembers()

        //handles moving between the fragments via the bottom menu
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_view) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {

                R.id.nav_home -> {
                    navController.navigate(R.id.homeFragment)
                }

                R.id.nav_parties -> {
                    navController.navigate(R.id.partyFragment)
                }

                R.id.nav_all -> {
                    navController.navigate(R.id.listFragment)
                }
            }
            true
        }
    }
}

