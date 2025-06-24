package com.example.akash_task

import androidx.room.*

@Dao
interface EmployeeDao {
    @Insert
    suspend fun insertEmployee(employee: EmployeeEntity)

    @Update
    suspend fun update(employee: EmployeeEntity)

    @Query("SELECT * FROM employee")
    suspend fun getAllEmployees(): List<EmployeeEntity>

    @Query("SELECT * FROM employee WHERE id = :id LIMIT 1")
    suspend fun getEmployeeById(id: Int): EmployeeEntity?
}
