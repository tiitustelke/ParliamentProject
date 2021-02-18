package com.example.oopproject1.fragments.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.oopproject1.API.ParliamentAPIService
import com.example.oopproject1.R
import com.example.oopproject1.data.MemberRepository
import com.example.oopproject1.data.MemberViewModel
import com.example.oopproject1.data.ParliamentMember
import com.example.oopproject1.databinding.FragmentListBinding
import com.example.oopproject1.databinding.FragmentParliamentMemberBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {

    private lateinit var memberViewModel: MemberViewModel
    //private lateinit var binding: FragmentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentListBinding>(
            inflater,
            R.layout.fragment_list,container,false
        )

        // Inflate the layout for this fragment

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        memberViewModel = ViewModelProvider(this).get(MemberViewModel::class.java)
        if(memberViewModel.getMembers().value.isNullOrEmpty()) {
            insertDataToDataBase()
        }
    }

    private fun insertDataToDataBase() {

        memberViewModel.addMembers()

        Toast.makeText(requireContext(), "success!",Toast.LENGTH_LONG).show()
    }



}