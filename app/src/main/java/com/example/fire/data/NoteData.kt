package com.example.fire.data

import android.icu.text.CaseMap.Title
import android.os.Parcel
import android.os.Parcelable

data class NoteData(
    val userId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val noteId: String? = null,
    val time: Long? = null
)