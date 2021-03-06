package com.example.oopproject1.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.oopproject1.R
import com.example.oopproject1.data.MemberViewModel
import com.example.oopproject1.data.ParliamentMember
import com.example.oopproject1.data.PartyMemberViewModel
import kotlinx.android.synthetic.main.member_row.view.*
import kotlinx.android.synthetic.main.member_row.view.firstNameView
import kotlinx.android.synthetic.main.member_row.view.lastNameView
import kotlinx.android.synthetic.main.partymember_row.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

//https://www.youtube.com/watch?v=wKFJsrdiGS8

class PartyMemberAdapter(private val listener: OnItemClickListener, val viewModel: PartyMemberViewModel): RecyclerView.Adapter<PartyMemberAdapter.PartyMemberHolder>() {

    private var memberList = emptyList<ParliamentMember>()
    inner class PartyMemberHolder(itemView: View): RecyclerView.ViewHolder(itemView),
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyMemberHolder {
        return PartyMemberHolder(LayoutInflater.from(parent.context).inflate(R.layout.partymember_row, parent, false))
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    override fun onBindViewHolder(holder: PartyMemberHolder, position: Int) {
        val selectedItem = memberList[position]
        holder.itemView.firstNameView.text = selectedItem.firstname
        holder.itemView.lastNameView.text = selectedItem.lastname

        if(selectedItem.minister) holder.itemView.titleView.text = "Ministeri"
        else holder.itemView.titleView.text = "Kansanedustaja"
        holder.itemView.partyMemberFace.setImageResource(R.drawable.sale)
        GlobalScope.launch(Dispatchers.IO) { getImage(selectedItem,holder) }
    }

    fun setMemberData(member: List<ParliamentMember>) {
        this.memberList = member
        notifyDataSetChanged()
    }

    suspend fun getImage(member: ParliamentMember, holder: PartyMemberHolder) {
        val image = viewModel.getImage(member)
        GlobalScope.launch(Dispatchers.Main) { holder.itemView.partyMemberFace.setImageBitmap(image) }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


}

