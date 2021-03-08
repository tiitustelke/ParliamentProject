package com.example.oopproject1.fragments.partymembers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oopproject1.R
import com.example.oopproject1.data.ParliamentMember
import kotlinx.android.synthetic.main.member_row.view.firstNameView
import kotlinx.android.synthetic.main.member_row.view.lastNameView
import kotlinx.android.synthetic.main.partymember_row.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * RecycleView adapter for showing members of a party. I have used this tutorial: https://www.youtube.com/watch?v=wKFJsrdiGS8. Thank you Coding in FLow from Youtube.
 */

class PartyMemberAdapter(private val listener: OnItemClickListener, private val viewModel: PartyMemberViewModel): RecyclerView.Adapter<PartyMemberAdapter.PartyMemberHolder>() {

    private var memberList = emptyList<ParliamentMember>()
    private lateinit var context: Context
    inner class PartyMemberHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }
        //get the position of the clicked item and notify the listener in PartyMemberList
        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyMemberHolder {
        context = parent.context
        return PartyMemberHolder(LayoutInflater.from(context).inflate(R.layout.partymember_row, parent, false))
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    override fun onBindViewHolder(holder: PartyMemberHolder, position: Int) {
        val selectedItem = memberList[position]

        holder.itemView.partyMemberFace.setImageResource(R.drawable.loading_animation)      //set image while data is loading
        GlobalScope.launch(Dispatchers.IO) { setItemView(selectedItem,holder) }
    }

    fun setMemberData(member: List<ParliamentMember>) {
        this.memberList = member
        notifyDataSetChanged()
    }

    private suspend fun setItemView(member: ParliamentMember, holder: PartyMemberHolder) {
        val image = viewModel.getImage(member)
        GlobalScope.launch(Dispatchers.Main) {
            holder.itemView.partyMemberFace.setImageBitmap(image)
            holder.itemView.firstNameView.text = member.firstname
            holder.itemView.lastNameView.text = member.lastname

            if(member.minister) holder.itemView.titleView.text = context.resources.getString(R.string.minister)
            else holder.itemView.titleView.text = context.resources.getString(R.string.member)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


}

