package com.overheat.capstoneproject.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comment")
data class Comment(

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "article_id")
    var articleId: Int,

    @ColumnInfo(name = "user_id")
    var user_id: Int,

    @ColumnInfo(name = "comment")
    var comment: String,

    @ColumnInfo(name = "date_added")
    var dateAdded: String
)