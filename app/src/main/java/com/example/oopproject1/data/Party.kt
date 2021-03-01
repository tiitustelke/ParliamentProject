package com.example.oopproject1.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Party (
        val abbr: String,
        val name: String,
        val logoID: Int
): Parcelable

