package com.example.akash_task

object EmployeeRepository {
    private val employeeList = mutableListOf<Employee>()

    fun addEmployee(employee: Employee) {
        employeeList.add(employee)
    }

    fun getAllEmployees(): List<Employee> {
        return employeeList
    }

    fun getCount(): Int = employeeList.size
}
