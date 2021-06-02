package com.overheat.capstoneproject.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.overheat.capstoneproject.core.data.source.local.entity.*

@Database(
    entities = [
        ArticleEntity::class,
        CommentEntity::class,
        FaqEntity::class,
        HistoryEntity::class,
        ResultEntity::class,
        UserEntity::class,
        DiagnoseEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class SkinCancerDatabase : RoomDatabase() {

    abstract fun skinCancerDao() : SkinCancerDao
}