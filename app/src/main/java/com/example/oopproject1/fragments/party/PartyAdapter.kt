package com.example.oopproject1.fragments.party


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oopproject1.R
import com.example.oopproject1.data.Party
import kotlinx.android.synthetic.main.party_row.view.*

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * RecycleView adapter for showing parties. I have used this tutorial: https://www.youtube.com/watch?v=wKFJsrdiGS8. Thank you Coding in FLow from Youtube.
 */
class PartyAdapter(private val listener: OnItemClickListener): RecyclerView.Adapter<PartyAdapter.PartyViewHolder>() {

    private var partyList = emptyList<Party>()
    inner class PartyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        //get the position of the clicked item and notify the listener in PartyFragment
        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyViewHolder {
        return PartyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.party_row, parent, false))
    }

    override fun getItemCount(): Int {
        return partyList.size
    }

    override fun onBindViewHolder(holder: PartyViewHolder, position: Int) {
        val party = partyList[position]
        val selectedItem = party.name
        holder.itemView.partyName.text = selectedItem

        holder.itemView.logoView.setImageResource(party.logoID)
    }

    fun setPartyData(parties: List<Party>) {
        this.partyList = parties
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


}

