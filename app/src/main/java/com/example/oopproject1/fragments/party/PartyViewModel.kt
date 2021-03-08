package com.example.oopproject1.fragments.party

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope

import com.example.oopproject1.R
import com.example.oopproject1.data.MemberDataBase
import com.example.oopproject1.data.MemberRepository
import com.example.oopproject1.data.Party
import com.example.oopproject1.data.PartyData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * Viewmodel for PartyFragment. Get parties from MemberRepository and get full names and logos ids of the parties, adding parties even if they are not PartyData object in case there are new parties
 */
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
            val parties = getParties()                  //get list of parties in the database
            val partyData = PartyData.parties            //get Parties from PartyData object
            val data = partyData.map { it.abbr }        //get abbreviations of parties in PartyData object

            parties.forEach { newParty ->
                if (data.contains(newParty)) {
                    val party = partyData.first { it.abbr == newParty }         //search the details of the party with the abbreviation
                    partyNames.add(party)
                }
                else {
                    partyNames.add(Party(newParty,newParty,R.drawable.ic_broken_image))     //if a new party is the database but is not in PartyData, add it to the list
                }
            }
        }
        return partyNames
    }

}