package com.example.oopproject1.fragments.member

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oopproject1.R
import com.example.oopproject1.data.Comment
import com.example.oopproject1.data.ParliamentMember
import com.example.oopproject1.data.PartyData
import com.example.oopproject1.databinding.FragmentParliamentMemberBinding
import com.example.oopproject1.fragments.partymembers.PartyMemberAdapter
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
    private var comments = arrayListOf<String>()
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

        GlobalScope.launch(Dispatchers.IO) { setImageAndLikes() }
        setMember()

        val adapter = CommentAdapter()
        val recyclerView = binding.commentView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getComments(member.hetekaId).observe(viewLifecycleOwner, Observer { Comment ->
            adapter.setCommentData(Comment)
        })

        binding.sendButton.setOnClickListener {
            if (!binding.editTextComment.text.isNullOrBlank()) {
                viewModel.addComment(Comment(0, member.hetekaId, binding.editTextComment.text.toString()))
            }
        }

        var voted = false

        binding.likeButton.setOnClickListener {

            if (voted) {
                binding.dislikeButton.visibility = VISIBLE
                voted = false
            }
            else {
                binding.likeButton.visibility = INVISIBLE
                voted = true
            }
            addLike()
        }

        binding.dislikeButton.setOnClickListener {

            if (voted) {
                binding.likeButton.visibility = VISIBLE
                voted = false
            }
            else {
                binding.dislikeButton.visibility = INVISIBLE
                voted = true
            }
            removeLike()
        }

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

    private fun addLike() {
       viewModel.plusVote(member.hetekaId)
       likes++
       binding.likes = likes.toString()
   }

    private fun removeLike() {
        viewModel.minusVote(member.hetekaId)
        likes--
        binding.likes = likes.toString()
    }

   private fun setMember() {
       val party = PartyData.parties.first { it.abbr == member.party }

       binding.partyImage.setImageResource(party.logoID)
       if (member.minister) binding.memberTitle.text = "Ministeri"
       else binding.memberTitle.text = "Kansanedustaja"

       binding.party = party.name
       binding.firstName = member.firstname
       binding.lastName = member.lastname

       binding.likes = likes.toString()
    }


    private suspend fun setImageAndLikes() {
        likes = viewModel.getVotes(member.hetekaId)
        val image = viewModel.getImage(member)
        GlobalScope.launch(Dispatchers.Main) {
            binding.likes = likes.toString()
            binding.photo.setImageBitmap(image)
        }
    }
}