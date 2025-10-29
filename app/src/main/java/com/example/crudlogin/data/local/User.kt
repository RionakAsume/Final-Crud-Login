package com.example.crudlogin.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val lastName: String,
    val dni: String,
    val phone: String,
    val email: String,
    val password: String,
    @ColumnInfo(defaultValue = "0")
    val estado: Boolean = false // false = activo, true = inactivo
)
