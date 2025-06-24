package com.example.akash_task

import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewEmployeeActivity : AppCompatActivity() {

    private lateinit var firstName: EditText
    private lateinit var middleName: EditText
    private lateinit var lastName: EditText
    private lateinit var phone: EditText
    private lateinit var email: EditText
    private lateinit var department: EditText
    private lateinit var designation: EditText
    private lateinit var saveButton: Button

    private lateinit var firstNameError: TextView
    private lateinit var middleNameError: TextView
    private lateinit var lastNameError: TextView
    private lateinit var phoneError: TextView
    private lateinit var emailError: TextView
    private lateinit var departmentError: TextView
    private lateinit var designationError: TextView

    private var employeeId: Int? = null // Used for update logic

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

        // Error TextViews
        firstNameError = findViewById(R.id.firstNameError)
        middleNameError = findViewById(R.id.middleNameError)
        lastNameError = findViewById(R.id.lastNameError)
        phoneError = findViewById(R.id.phoneError)
        emailError = findViewById(R.id.emailError)
        departmentError = findViewById(R.id.departmentError)
        designationError = findViewById(R.id.designationError)

        // Filters
        phone.filters = arrayOf(InputFilter.LengthFilter(10), PhoneNumberFilter())

        // Real-time validation
        addTextWatchers()

        // Check for edit
        employeeId = intent.getIntExtra("employee_id", -1).takeIf { it != -1 }

        employeeId?.let { id ->
            lifecycleScope.launch {
                val emp = withContext(Dispatchers.IO) {
                    RoomEmployeeRepository(this@NewEmployeeActivity).getEmployeeById(id)
                }
                emp?.let {
                    firstName.setText(it.firstName)
                    middleName.setText(it.middleName)
                    lastName.setText(it.lastName)
                    phone.setText(it.phone)
                    email.setText(it.email)
                    department.setText(it.department)
                    designation.setText(it.designation)
                }
            }
        }

        saveButton.setOnClickListener {
            if (validateInput()) {
                val entity = EmployeeEntity(
                    id = employeeId ?: 0, // required for update
                    firstName = firstName.text.toString(),
                    middleName = middleName.text.toString(),
                    lastName = lastName.text.toString(),
                    phone = phone.text.toString(),
                    email = email.text.toString(),
                    department = department.text.toString(),
                    designation = designation.text.toString()
                )

                lifecycleScope.launch(Dispatchers.IO) {
                    val repo = RoomEmployeeRepository(this@NewEmployeeActivity)
                    if (employeeId != null) {
                        repo.updateEmployee(entity)
                    } else {
                        repo.addEmployee(entity)
                    }

                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@NewEmployeeActivity,
                            if (employeeId != null) "Employee updated!" else "Employee added!",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        fun showError(view: TextView, message: String) {
            view.text = message
            view.visibility = View.VISIBLE
        }

        // Hide all errors first
        listOf(
            firstNameError, middleNameError, lastNameError,
            phoneError, emailError, departmentError, designationError
        ).forEach { it.visibility = View.GONE }

        if (firstName.text.isBlank()) {
            showError(firstNameError, "First name required")
            isValid = false
        }
        if (middleName.text.isBlank()) {
            showError(middleNameError, "Middle name required")
            isValid = false
        }
        if (lastName.text.isBlank()) {
            showError(lastNameError, "Last name required")
            isValid = false
        }
        val phoneValue = phone.text.toString()
        if (phoneValue.length != 10 || phoneValue[0] in '0'..'4') {
            showError(phoneError, "Phone must be 10 digits and not start with 0-4")
            isValid = false
        }
        if (!email.text.contains("@")) {
            showError(emailError, "Invalid email format")
            isValid = false
        }
        if (department.text.isBlank()) {
            showError(departmentError, "Department required")
            isValid = false
        }
        if (designation.text.isBlank()) {
            showError(designationError, "Designation required")
            isValid = false
        }

        return isValid
    }

    private fun addTextWatchers() {
        firstName.liveValidate(firstNameError, "First name required") { it.isNotBlank() }
        middleName.liveValidate(middleNameError, "Middle name required") { it.isNotBlank() }
        lastName.liveValidate(lastNameError, "Last name required") { it.isNotBlank() }
        phone.liveValidate(phoneError, "Must be 10 digits and not start with 0-4") {
            it.length == 10 && it[0] !in '0'..'4'
        }
        email.liveValidate(emailError, "Invalid email format") { it.contains("@") }
        department.liveValidate(departmentError, "Department required") { it.isNotBlank() }
        designation.liveValidate(designationError, "Designation required") { it.isNotBlank() }
    }

    // Custom phone number input filter
    class PhoneNumberFilter : InputFilter {
        override fun filter(
            source: CharSequence?, start: Int, end: Int,
            dest: Spanned?, dstart: Int, dend: Int
        ): CharSequence? {
            val result = (dest?.toString() ?: "") + (source?.toString() ?: "")
            if (result.length == 1 && result[0] in '0'..'4') {
                return ""
            }
            if (source != null && source.any { !it.isDigit() }) {
                return ""
            }
            return null
        }
    }
}
