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

        parties.forEach {
            when(it) {

                "kd" -> {
                    partyNames.add(Party(it,"Suomen Kristillisdemokraatit", R.drawable.kd_logo))
                }

                "kesk" -> {
                    partyNames.add(Party(it,"Suomen Keskusta",R.drawable.kesk_logo))
                }

                "kok" -> {
                    partyNames.add(Party(it,"Kansallinen Kokoomus",R.drawable.kok_logo))
                }

                "liik" -> {
                    partyNames.add(Party(it,"Liike Nyt",R.drawable.liik_logo))
                }

                "ps" -> {
                    partyNames.add(Party(it,"Perussuomalaiset",R.drawable.ps_logo))
                }

                "r" -> {
                    partyNames.add(Party(it,"Suomen ruotsalainen kansanpuolue",R.drawable.r_logo))
                }

                "sd" -> {
                    partyNames.add(Party(it,"Suomen Sosiaalidemokraattinen Puolue",R.drawable.sd_log))
                }

                "vas" -> {
                    partyNames.add(Party(it,"Vasemmistoliitto",R.drawable.vas_logo))
                }

                "vihr" -> {
                    partyNames.add(Party(it,"Vihreä liitto",R.drawable.vihr_logo))
                }

                else -> {
                    partyNames.add(Party(it,it,R.drawable.sale))
                }

            }
        }
        }
        return partyNames
    }

}