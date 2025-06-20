package com.example.akash_task

data class Employee(
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val department: String,
    val designation: String
) {
    fun fullName(): String {
        return "$firstName $middleName $lastName"
    }
}