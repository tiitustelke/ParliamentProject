package com.example.oopproject1.fragments.party

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.oopproject1.API.ParliamentApi
import com.example.oopproject1.R
import com.example.oopproject1.data.MemberDataBase
import com.example.oopproject1.data.MemberRepository
import com.example.oopproject1.data.Party
import com.example.oopproject1.data.PartyData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PartyViewModel(application: Application): AndroidViewModel(application) {

    private val repository: MemberRepository

    init {
        val memberDao = MemberDataBase.getDatabase(application).memberDao()
        repository = MemberRepository(memberDao)
    }

    fun getParties() = repository.getParties()

    fun getPartyNames(): List<Party> {
        val partyNames = mutableListOf<Party>()
        viewModelScope.launch(Dispatchers.IO) {
        val parties = getParties()
        val partyData = PartyData.parties
            val data = partyData.map { it.abbr }
        parties.forEach { newParty ->

            if (data.contains(newParty)) {
                val party = partyData.first { it.abbr == newParty }
                partyNames.add(party)
            }
            else {
                partyNames.add(Party(newParty,newParty,R.drawable.sale))
            }
            }
        }
        return partyNames
    }

}