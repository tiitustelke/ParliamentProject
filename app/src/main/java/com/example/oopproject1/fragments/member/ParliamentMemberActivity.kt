package com.example.oopproject1.fragments.member

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.oopproject1.R
import com.example.oopproject1.data.ParliamentMember
import com.example.oopproject1.databinding.FragmentParliamentMemberBinding
import kotlinx.coroutines.*

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
    private val args by navArgs<ParliamentMemberActivityArgs>()
    private lateinit var viewModel: ParliamentMemberViewModel
    private lateinit var binding: FragmentParliamentMemberBinding
    private lateinit var member: ParliamentMember

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        member = args.member
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ParliamentMemberViewModel::class.java)

        binding = DataBindingUtil.inflate<FragmentParliamentMemberBinding>(
            inflater, R.layout.fragment_parliament_member,container,false
        )
            //addParliamentMember(member)

        GlobalScope.launch(Dispatchers.IO) { setImage() }


        // Inflate the layout for this fragment
        return binding.root
    }
   /* fun getNewParliamentMember() {

        val rand = Random.nextInt(0,199)
        var member = ParliamentMembersData.members[counter]
        if (counter < 199) counter++
        name = (if(member.minister == true) "Ministeri " else "Kansanedustaja ") + member.firstname + " " + ParliamentMembersData.members[counter].lastname
        party = ParliamentMembersData.members[counter].party
        likes = Random.nextInt(-500,500)

    }*/
   private fun setMember(image: Bitmap?) {

        binding.firstName = member.firstname
       binding.lastName = member.lastname
        binding.party = member.party
        binding.likes = likes.toString()

        binding.buttonNext.setOnClickListener {
            binding.firstName = name
            binding.party = party
            binding.likes = likes.toString()
        }

       image?.let {   binding.photo.setImageBitmap(it) }
    }


    private suspend fun setImage() {

        val image = viewModel.getImage(member)
        GlobalScope.launch(Dispatchers.Main) {
            setMember(image)
        }
    }
}