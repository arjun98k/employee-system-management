package com.example.akash_task

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class NewEmployeeActivity : AppCompatActivity() {

    private lateinit var firstName: EditText
    private lateinit var middleName: EditText
    private lateinit var lastName: EditText
    private lateinit var phone: EditText
    private lateinit var email: EditText
    private lateinit var department: EditText
    private lateinit var designation: EditText
    private lateinit var saveButton: Button

    // Error TextViews
    private lateinit var firstNameError: TextView
    private lateinit var middleNameError: TextView
    private lateinit var lastNameError: TextView
    private lateinit var phoneError: TextView
    private lateinit var emailError: TextView
    private lateinit var departmentError: TextView
    private lateinit var designationError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_employee)

        // Form Inputs
        firstName = findViewById(R.id.firstName)
        middleName = findViewById(R.id.middleName)
        lastName = findViewById(R.id.lastName)
        phone = findViewById(R.id.phone)
        email = findViewById(R.id.email)
        department = findViewById(R.id.department)
        designation = findViewById(R.id.designation)
        saveButton = findViewById(R.id.saveButton)

        // Error Labels
        firstNameError = findViewById(R.id.firstNameError)
        middleNameError = findViewById(R.id.middleNameError)
        lastNameError = findViewById(R.id.lastNameError)
        phoneError = findViewById(R.id.phoneError)
        emailError = findViewById(R.id.emailError)
        departmentError = findViewById(R.id.departmentError)
        designationError = findViewById(R.id.designationError)

        saveButton.setOnClickListener {
            if (validateInput()) {
                val employee = Employee(
                    firstName.text.toString(),
                    middleName.text.toString(),
                    lastName.text.toString(),
                    phone.text.toString(),
                    email.text.toString(),
                    department.text.toString(),
                    designation.text.toString()
                )
                EmployeeRepository.addEmployee(employee)
                Toast.makeText(this, "Employee added!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        // Hide all error labels first
        firstNameError.visibility = View.GONE
        middleNameError.visibility = View.GONE
        lastNameError.visibility = View.GONE
        phoneError.visibility = View.GONE
        emailError.visibility = View.GONE
        departmentError.visibility = View.GONE
        designationError.visibility = View.GONE

        if (firstName.text.isBlank()) {
            firstNameError.visibility = View.VISIBLE
            isValid = false
        }

        if (middleName.text.isBlank()) {
            middleNameError.visibility = View.VISIBLE
            isValid = false
        }

        if (lastName.text.isBlank()) {
            lastNameError.visibility = View.VISIBLE
            isValid = false
        }

        if (phone.text.length != 10) {
            phoneError.text = "Phone number must be 10 digits"
            phoneError.visibility = View.VISIBLE
            isValid = false
        }

        if (!email.text.contains("@")) {
            emailError.text = "Invalid email address"
            emailError.visibility = View.VISIBLE
            isValid = false
        }

        if (department.text.isBlank()) {
            departmentError.visibility = View.VISIBLE
            isValid = false
        }

        if (designation.text.isBlank()) {
            designationError.visibility = View.VISIBLE
            isValid = false
        }

        return isValid
    }
}
