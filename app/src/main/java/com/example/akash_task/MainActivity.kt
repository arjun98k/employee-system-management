package com.example.akash_task
import com.example.akash_task.EmployeeRepository

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    private lateinit var totalText: TextView
    private lateinit var container: LinearLayout
    private lateinit var fabAdd: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_employee_list)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        totalText = findViewById(R.id.totalEmployees)
        container = findViewById(R.id.employeeListContainer) // <== Add android:id="employeeListContainer" to your XML
        fabAdd = findViewById(R.id.fabAddEmployee)

        fabAdd.setOnClickListener {
            startActivity(Intent(this, NewEmployeeActivity::class.java))
        }

        findViewById<TextView>(R.id.csvExport).setOnClickListener {
            Toast.makeText(this, "CSV Export (not implemented)", Toast.LENGTH_SHORT).show()
        }

        findViewById<TextView>(R.id.pdfExport).setOnClickListener {
            Toast.makeText(this, "PDF Export (not implemented)", Toast.LENGTH_SHORT).show()
        }

        refreshList()
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun refreshList() {
        container.removeAllViews()
        val employees = EmployeeRepository.getAllEmployees()
        totalText.text = "Total Employees: ${employees.size}"
        for (emp in employees) {
            val view = layoutInflater.inflate(R.layout.item_employee_card, container, false)

            view.findViewById<TextView>(R.id.empName).text = emp.fullName()
            view.findViewById<TextView>(R.id.empPhone).text = emp.phone
            view.findViewById<TextView>(R.id.empEmail).text = emp.email
            view.findViewById<TextView>(R.id.empId).text = (1000..9999).random().toString()
            view.findViewById<TextView>(R.id.empRole).text = emp.designation
            view.findViewById<TextView>(R.id.empDept).text = emp.department

            container.addView(view)
        }
    }
}
