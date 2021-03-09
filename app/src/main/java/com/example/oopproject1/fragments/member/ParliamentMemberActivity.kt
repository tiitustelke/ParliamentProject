package com.example.oopproject1.fragments.member

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
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
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * This class is for showing ParliamentMemberActivity fragment and handling its user inputs. ParliamentMemberActivity fragment shows details of a parliamentmember and allows voting and commenting.
 */

class ParliamentMemberActivity : Fragment() {
    private val args by navArgs<ParliamentMemberActivityArgs>()

    private lateinit var viewModel: ParliamentMemberViewModel
    private lateinit var binding: FragmentParliamentMemberBinding
    private lateinit var member: ParliamentMember   //member that will be shown

    private var likes: Int = 0
    private var userName: String = ""

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
        //fetch and set data for this activity's views
        GlobalScope.launch(Dispatchers.IO) { setImageAndLikes() }
        setMember()

        //recyclerview for comments
        val adapter = CommentAdapter()
        val recyclerView = binding.commentView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //observer for comments
        viewModel.getComments(member.hetekaId).observe(viewLifecycleOwner, Observer { Comment ->
            adapter.setCommentData(Comment)
        })

        //listeners for adding a new comment after clicking send or button on keyboard
        binding.sendButton.setOnClickListener { addComment() }
        binding.editTextComment.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                addComment()
            }
            false
        })

        var voted = false

        binding.likeButton.setOnClickListener {
            //if disliked earlier, and likebutton is clicked, set dislikebutton visible ("cancel" dislike)
            if (voted) {
                binding.dislikeButton.visibility = VISIBLE
                voted = false
            }
            //if user hasn't voted yet, after voting set likebutton invisible
            else {
                binding.likeButton.visibility = INVISIBLE
                voted = true
            }
            addLike()
        }

        binding.dislikeButton.setOnClickListener {
            //if liked earlier, and dislikebutton is clicked, set likebutton visible ("cancel" like)
            if (voted) {
                binding.likeButton.visibility = VISIBLE
                voted = false
            }
            //if user hasn't voted yet, after voting set dislikebutton invisible
            else {
                binding.dislikeButton.visibility = INVISIBLE
                voted = true
            }
            removeLike()
        }

        return binding.root
    }

    //get necessary data for a comment and add it database
    private fun addComment() {
        if (!binding.editTextComment.text.isNullOrBlank()) {
            getUserName()
            val time = SimpleDateFormat("d.M.yyyy", Locale.getDefault()).format(Date()) + " klo. " + SimpleDateFormat("H.mm", Locale.getDefault()).format(Date())
            viewModel.addComment(Comment(0, member.hetekaId, userName, binding.editTextComment.text.toString(), time))
            binding.editTextComment.text.clear()
        }
    }

    //get username from shared preferences
    private fun getUserName() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.name_not_set)
        userName = sharedPref.getString(getString(R.string.user_pref), defaultValue) ?: defaultValue
    }
    //add like for the member to the database
    private fun addLike() {
       viewModel.plusVote(member.hetekaId)
       likes++
       binding.likes = likes.toString()
   }
    //remove a like for the member to the database
    private fun removeLike() {
        viewModel.minusVote(member.hetekaId)
        likes--
        binding.likes = likes.toString()
    }
    //set data for the views excluding photo and likes
   private fun setMember() {
       val party = PartyData.parties.first { it.abbr == member.party }  //search the PartyData object for the logo and name of the member

       binding.partyImage.setImageResource(party.logoID)
       if (member.minister) binding.memberTitle.text = resources.getString(R.string.minister)
       else binding.memberTitle.text = resources.getString(R.string.member)

       binding.party = party.name
       binding.firstName = member.firstname
       binding.lastName = member.lastname

       binding.likes = likes.toString()
    }

    //get votes and photo of the member from the database and set them in main thread
    private suspend fun setImageAndLikes() {
        likes = viewModel.getVotes(member.hetekaId)
        val image = viewModel.getImage(member)
        GlobalScope.launch(Dispatchers.Main) {
            binding.likes = likes.toString()
            binding.photo.setImageBitmap(image)
        }
    }
}