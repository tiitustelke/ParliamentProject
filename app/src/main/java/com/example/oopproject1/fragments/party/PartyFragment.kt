package com.example.oopproject1.fragments.party

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.oopproject1.R
import com.example.oopproject1.data.Party
import com.example.oopproject1.databinding.FragmentPartyBinding

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * This class is for showing PartyFragment and handling its user actions. PartyFragment lists parties and allows the user to move to PartyMemberList fragment.
 */
//implements adapters OnCLickListener interface, so recyclerview clicks call the overrided onItemClick() -method
class PartyFragment : Fragment(), PartyAdapter.OnItemClickListener {
    private lateinit var binding: FragmentPartyBinding
    private lateinit var partyViewModel: PartyViewModel
    private lateinit var parties: List<Party>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        partyViewModel = ViewModelProvider(this).get(PartyViewModel::class.java)
        parties = partyViewModel.getPartyNames()        //get logo and full name of parties from PartyData object and database
    }
    //called when a recyclerview item is clicked
    override fun onItemClick(position: Int) {
        //get the party that was clicked, send it as safearg to PartyMemberList
        val party = parties[position]
        val action = PartyFragmentDirections.actionPartyFragmentToPartyMemberList(party)

        findNavController().navigate(action)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentPartyBinding>(
                inflater, R.layout.fragment_party,container,false
        )

        //recyclerview for showing parties
        val adapter = PartyAdapter(this)
        val recyclerView = binding.partyListView
        recyclerView.adapter = adapter

        //recyclerview set two gridlayout with 2 columns
        val manager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = manager

        adapter.setPartyData(parties)
        
        return binding.root
    }


}