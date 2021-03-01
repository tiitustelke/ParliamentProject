package com.example.oopproject1.fragments.party

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oopproject1.R
import com.example.oopproject1.data.Party
import com.example.oopproject1.databinding.FragmentPartyBinding

/**
 * A simple [Fragment] subclass.
 * Use the [PartyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PartyFragment : Fragment(), PartyAdapter.OnItemClickListener {
    private lateinit var binding: FragmentPartyBinding
    private lateinit var partyViewModel: PartyViewModel
    private lateinit var parties: List<Party>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        partyViewModel = ViewModelProvider(this).get(PartyViewModel::class.java)
        parties = partyViewModel.getPartyNames()
    }

    override fun onItemClick(position: Int) {
        val party = parties[position]
        val action = PartyFragmentDirections.actionPartyFragmentToListFragment(party)

        findNavController().navigate(action)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentPartyBinding>(
                inflater, R.layout.fragment_party,container,false
        )

        val adapter = PartyAdapter(this)
        val recyclerView = binding.partyView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.setPartyData(parties)

        // Inflate the layout for this fragment
        return binding.root
    }


}