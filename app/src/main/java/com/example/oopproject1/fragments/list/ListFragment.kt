package com.example.oopproject1.fragments.list

import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oopproject1.R
import com.example.oopproject1.data.MemberViewModel
import com.example.oopproject1.databinding.FragmentListBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment(), ListAdapter.OnItemClickListener {

    private lateinit var memberViewModel: MemberViewModel
    private lateinit var binding: FragmentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memberViewModel = ViewModelProvider(this).get(MemberViewModel::class.java)

    }

    override fun onItemClick(position: Int) {

        val action = ListFragmentDirections.actionListFragmentToParliamentMemberActivity(position)
        findNavController().navigate(action)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentListBinding>(
            inflater,
            R.layout.fragment_list,container,false
        )

        val adapter = ListAdapter(this)
        val recyclerView = binding.memberView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        memberViewModel.getMembers().observe(viewLifecycleOwner, Observer { ParliamentMember ->
            adapter.setMemberData(ParliamentMember)
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(memberViewModel.getMembers().value.isNullOrEmpty()) {
            addMembersToDataBase()
        }
    }

    private fun addMembersToDataBase() {
        if(memberViewModel.addMembers()) {
            Toast.makeText(requireContext(), "Success!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), "Adding members failed!", Toast.LENGTH_LONG).show()
        }
    }

    private fun getMember() {

    }

    private suspend fun updateMembers() {
        memberViewModel.deleteMembers().await()
        addMembersToDataBase()
    }

}