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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oopproject1.R
import com.example.oopproject1.data.ParliamentMember
import com.example.oopproject1.databinding.FragmentListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * This class is for showing ListFragment and handling its user inputs. List fragment shows all parliament members. Clicking a member navigates the user to ParliamentMemberActivity
 */

//implements adapters OnCLickListener interface, so recyclerview clicks call the overrided onItemClick() -method
class ListFragment : Fragment(), ListAdapter.OnItemClickListener {

    private lateinit var memberViewModel: ListViewModel
    private lateinit var binding: FragmentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memberViewModel = ViewModelProvider(this).get(ListViewModel::class.java)

    }
    //called when a recyclerview item is clicked
    override fun onItemClick(position: Int) {
        //get the ParliamentMember that was clicked, send it as safearg to ParliamentmemberActivity
        GlobalScope.launch(Dispatchers.IO) {
            val member = memberViewModel.getMember(position).await()
            val action = ListFragmentDirections.actionListFragmentToParliamentMemberActivity(member)
            findNavController().navigate(action)
        }
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

        //observe ParliamentMembers from room database and update them to ListAdapter.
        memberViewModel.getMembers().observe(viewLifecycleOwner, Observer { ParliamentMember ->
            adapter.setMemberData(ParliamentMember)
        })

        return binding.root
    }
}