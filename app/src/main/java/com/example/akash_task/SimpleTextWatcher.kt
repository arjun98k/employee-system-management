package com.example.akash_task

import android.text.Editable
import android.text.TextWatcher

class SimpleTextWatcher(val onTextChanged: (String) -> Unit) : TextWatcher {
    override fun afterTextChanged(s: Editable?) = Unit
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged.invoke(s.toString())
    }
}
