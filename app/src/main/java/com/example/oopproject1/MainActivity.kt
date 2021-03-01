package com.example.oopproject1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.oopproject1.data.MemberViewModel
import com.example.oopproject1.databinding.ActivityMainBinding
import com.example.oopproject1.fragments.list.ListFragmentDirections

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var memberViewModel: MemberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        memberViewModel = ViewModelProvider(this).get(MemberViewModel::class.java)

        val view = binding.root
        setContentView(view)

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
        checkDataIsValid()
    }

    fun checkDataIsValid() {

        if(memberViewModel.getMembers().value.isNullOrEmpty()) {
            addMembersToDataBase()
        }
    }

    private fun addMembersToDataBase() {
        val context = this
        if(memberViewModel.addMembers()) {
            Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Adding members failed!", Toast.LENGTH_LONG).show()
        }
    }

    private fun getMember() {

    }

    private suspend fun updateMembers() {
        memberViewModel.deleteMembers().await()
        addMembersToDataBase()
    }

}