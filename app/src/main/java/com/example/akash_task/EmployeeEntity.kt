package com.example.akash_task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val department: String,
    val designation: String
)
