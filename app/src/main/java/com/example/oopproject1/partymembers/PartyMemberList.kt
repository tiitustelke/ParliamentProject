package com.example.oopproject1.fragments.list

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
import com.example.oopproject1.data.PartyMemberViewModel
import com.example.oopproject1.data.ParliamentMember
import com.example.oopproject1.databinding.FragmentListBinding
import com.example.oopproject1.databinding.FragmentPartyMemberListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PartyMemberList : Fragment(), PartyMemberAdapter.OnItemClickListener {

    private lateinit var partyMemberViewModel: PartyMemberViewModel
    private lateinit var binding: FragmentPartyMemberListBinding
    private val args: PartyMemberListArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        partyMemberViewModel = ViewModelProvider(this).get(PartyMemberViewModel::class.java)

    }

    override fun onItemClick(position: Int) {
        var member: ParliamentMember
        val party = args.party
        GlobalScope.launch(Dispatchers.IO) {
            member = partyMemberViewModel.getMemberByParty(party.abbr,position).await()

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

        val adapter = PartyMemberAdapter(this,partyMemberViewModel)
        val recyclerView = binding.partyMemberView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val party = args.party

        partyMemberViewModel.getMembersByParty(party.abbr).observe(viewLifecycleOwner, Observer { ParliamentMember ->
                adapter.setMemberData(ParliamentMember)
            })

        return binding.root
    }
}