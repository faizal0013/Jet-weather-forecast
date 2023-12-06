package com.demo.jetweatherforecast.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Unit(
    @NonNull
    @PrimaryKey
    @ColumnInfo val unit: String
)
