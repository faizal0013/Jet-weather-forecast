package com.demo.jetweatherforecast.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @NonNull
    @PrimaryKey
    @ColumnInfo val city: String,
    @ColumnInfo val country: String,
)