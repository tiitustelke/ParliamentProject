package com.example.oopproject1.data

import android.os.Parcelable
import com.example.oopproject1.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Party (
        val abbr: String,
        val name: String,
        val logoID: Int
): Parcelable

object PartyData {
    val parties = listOf(
            Party(
                    abbr = "kd",
                    name = "Suomen Kristillisdemokraatit",
                    logoID = R.drawable.kd_logo
            ),
            Party(
                    abbr = "kesk",
                    name = "Suomen Keskusta",
                    logoID = R.drawable.kesk_logo
            ),
            Party(
                    abbr = "kok",
                    name = "Kansallinen Kokoomus",
                    logoID = R.drawable.kok_logo
            ),
            Party(
                    abbr = "liik",
                    name = "Liike Nyt",
                    logoID = R.drawable.liik_logo
            ),
            Party(
                    abbr = "ps",
                    name = "Perussuomalaiset",
                    logoID = R.drawable.ps_logo
            ),
            Party(
                    abbr = "r",
                    name = "Suomen ruotsalainen kansanpuolue",
                    logoID = R.drawable.r_logo
            ),
            Party(
                    abbr = "sd",
                    name = "Suomen Sosiaalidemokraattinen Puolue",
                    logoID = R.drawable.sd_log
            ),
            Party(
                    abbr = "vas",
                    name = "Vasemmistoliitto",
                    logoID = R.drawable.vas_logo
            ),
            Party(
                    abbr = "vihr",
                    name = "Vihreä liitto",
                    logoID = R.drawable.vihr_logo
            )
    )
}

