package com.example.akash_task



import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView

fun EditText.liveValidate(
    errorView: TextView,
    errorMessage: String,
    validator: (String) -> Boolean
) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = Unit
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val input = s.toString()
            if (validator(input)) {
                errorView.visibility = View.GONE
            } else {
                errorView.text = errorMessage
                errorView.visibility = View.VISIBLE
            }
        }
    })
}
