package com.overheat.capstoneproject.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Result(

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "cancer_proba")
    var cancerProba: Float,

    @ColumnInfo(name = "user_id")
    var userId: Int,

    @ColumnInfo(name = "date_added")
    val dateAdded: String
)
