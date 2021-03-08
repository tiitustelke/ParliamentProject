package com.example.oopproject1.fragments.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.oopproject1.R
import com.example.oopproject1.databinding.FragmentHomeBinding

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * This class is for displaying and handling of user input in HomeFragment. Home fragment contains a short introduction for the app and allows the user to set a username for commenting
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home,container,false
        )

        //listener for clicking done button in keyboard
        binding.nameEditText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val name = v.text.toString()
                saveUserName(name)
                Toast.makeText(requireContext(), "Käyttäjänimi asetettu", Toast.LENGTH_SHORT).show()
                return@OnEditorActionListener true
            }
            false
        })

        return binding.root
    }

    //save username to sharedpreferences
    private fun saveUserName(userName: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(getString(R.string.user_pref), userName)
            apply()
        }
    }


}