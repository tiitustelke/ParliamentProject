package com.example.oopproject1.fragments.partymembers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oopproject1.R

import com.example.oopproject1.databinding.FragmentPartyMemberListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * This class is for showing PartyMemberList fragment and handling its user actions. PartyMemberList fragment lists parties and allows the user to navigate to ParliamentMemberActivity fragment.
 */
//implements adapters OnCLickListener interface, so recyclerview clicks call the overrided onItemClick() -method
class PartyMemberList : Fragment(), PartyMemberAdapter.OnItemClickListener {

    private lateinit var partyMemberViewModel: PartyMemberViewModel
    private lateinit var binding: FragmentPartyMemberListBinding
    private val args: PartyMemberListArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        partyMemberViewModel = ViewModelProvider(this).get(PartyMemberViewModel::class.java)
    }
    //called when a recyclerview item is clicked
    override fun onItemClick(position: Int) {
        //get the member that was clicked, send it as safearg to ParliamentMemberActivity
        val party = args.party
        GlobalScope.launch(Dispatchers.IO) {
            val member = partyMemberViewModel.getMemberByParty(party.abbr,position).await()     //get the member that was clicked with the recyclerview item position and abbreviation of the party

            val action = PartyMemberListDirections.actionPartyMemberListToParliamentMemberActivity(member)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentPartyMemberListBinding>(
                inflater,
                R.layout.fragment_party_member_list,container,false
        )

        //recyclerview adapter for listing members of a party
        val adapter = PartyMemberAdapter(this,partyMemberViewModel)
        val recyclerView = binding.partyMemberView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val party = args.party

        //observer for parliament member of a party
        partyMemberViewModel.getMembersByParty(party.abbr).observe(viewLifecycleOwner, Observer { ParliamentMember ->
            adapter.setMemberData(ParliamentMember)
        })

        return binding.root
    }
}