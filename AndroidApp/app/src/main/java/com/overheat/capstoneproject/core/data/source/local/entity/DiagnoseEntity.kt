package com.overheat.capstoneproject.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diagnose")
data class DiagnoseEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "image")
    var image: String?,

    @ColumnInfo(name = "user_id")
    var userId: Int?,

    @ColumnInfo(name = "cancer_proba")
    var cancerProba: Double,

    @ColumnInfo(name = "date_added")
    var dateAdded: String
)
