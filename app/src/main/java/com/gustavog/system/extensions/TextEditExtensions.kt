package com.gustavog.system.extensions

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.bindLiveData(
    lifecycleOwner: LifecycleOwner,
    liveData: LiveData<String>,
    onChanged: (String) -> Unit
) {

    liveData.observe(lifecycleOwner, { newString ->
        if (newString != this.text.toString()) {
            this.setText(newString)
        }
    })

    this.addTextChangedListener(object : TextWatcher {

        var oldText: String? = null

        override fun afterTextChanged(p0: Editable?) {
            // UNUSED
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            oldText = s.toString()
        }

        override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {
            string?.let { newString ->
                if (newString != oldText) {
                    onChanged.invoke(newString.toString())
                }
            }
        }

    })
}
