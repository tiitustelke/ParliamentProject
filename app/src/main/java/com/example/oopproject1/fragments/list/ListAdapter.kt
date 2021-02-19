package com.example.oopproject1.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oopproject1.R
import com.example.oopproject1.data.ParliamentMember
import kotlinx.android.synthetic.main.member_row.view.*

//https://www.youtube.com/watch?v=wKFJsrdiGS8

class ListAdapter(private val listener: OnItemClickListener): RecyclerView.Adapter<ListAdapter.MemberViewHolder>() {

    private var memberList = emptyList<ParliamentMember>()
    inner class MemberViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
    View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
            return MemberViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.member_row, parent, false))
        }

        override fun getItemCount(): Int {
            return memberList.size
        }

        override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
            val selectedItem = memberList[position]
            holder.itemView.party.text = selectedItem.party
            holder.itemView.firstNameView.text = selectedItem.firstname
            holder.itemView.lastNameView.text = selectedItem.lastname
        }

        fun setMemberData(member: List<ParliamentMember>) {
            this.memberList = member
            notifyDataSetChanged()
        }

        interface OnItemClickListener {
            fun onItemClick(position: Int)
        }


}

