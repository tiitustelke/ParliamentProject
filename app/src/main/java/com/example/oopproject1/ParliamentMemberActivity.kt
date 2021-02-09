package com.example.oopproject1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.oopproject1.databinding.FragmentParliamentMemberBinding
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [ParliamentMember.newInstance] factory method to
 * create an instance of this fragment.
 */
class ParliamentMemberActivity : Fragment() {
    // TODO: Rename and change types of parameters
    private var name: String= "nimi"
    private var likes: Int = 0
    private var party: String = "puolue"
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GetNewParliamentMember()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentParliamentMemberBinding>(
            inflater, R.layout.fragment_parliament_member,container,false)
        binding.name = name
        binding.party = party
        binding.likes = likes.toString()

        binding.buttonNext.setOnClickListener {
            GetNewParliamentMember()
            binding.name = name
            binding.party = party
            binding.likes = likes.toString()
        }
        // Inflate the layout for this fragment
        return binding.root
    }
    fun GetNewParliamentMember() {
        val rand = Random.nextInt(0,199)
        var member = ParliamentMembersData.members[counter]
        if (counter < 199) counter++
        name = (if(member.minister == true) "Ministeri " else "Kansanedustaja ") + member.firstname + " " + ParliamentMembersData.members[counter].lastname
        party = ParliamentMembersData.members[counter].party
        likes = Random.nextInt(-500,500)


    }
}