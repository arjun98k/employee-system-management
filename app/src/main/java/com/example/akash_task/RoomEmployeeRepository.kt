package com.example.akash_task

import android.content.Context

class RoomEmployeeRepository(context: Context) {

    private val employeeDao = AppDatabase.getDatabase(context).employeeDao()

    suspend fun addEmployee(employee: EmployeeEntity) {
        employeeDao.insertEmployee(employee)
    }

    suspend fun updateEmployee(employee: EmployeeEntity) {
        employeeDao.update(employee)
    }

    suspend fun getAllEmployees(): List<EmployeeEntity> {
        return employeeDao.getAllEmployees()
    }

    suspend fun getEmployeeById(id: Int): EmployeeEntity? {
        return employeeDao.getEmployeeById(id)
    }
}
