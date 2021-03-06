package com.example.oopproject1.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oopproject1.R
import com.example.oopproject1.data.MemberViewModel
import com.example.oopproject1.data.ParliamentMember
import com.example.oopproject1.databinding.FragmentListBinding
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
class ListFragment : Fragment(), ListAdapter.OnItemClickListener {

    private lateinit var memberViewModel: ListViewModel
    private lateinit var binding: FragmentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memberViewModel = ViewModelProvider(this).get(ListViewModel::class.java)

    }

    override fun onItemClick(position: Int) {
        var member: ParliamentMember

        GlobalScope.launch(Dispatchers.IO) {
            member = memberViewModel.getMember(position).await()
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

        memberViewModel.getMembers().observe(viewLifecycleOwner, Observer { ParliamentMember ->
            adapter.setMemberData(ParliamentMember)
        })

        return binding.root
    }
}