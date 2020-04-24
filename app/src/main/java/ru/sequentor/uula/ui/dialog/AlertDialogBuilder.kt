package ru.sequentor.uula.ui.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog


object AlertDialogBuilder {

    fun showError(
        context: Context,
        title: String,
        message: String,
        buttonPositive: String,
        buttonNegative: String = "",
        positive: () -> Unit,
        negative: () -> Unit = {}
    ) {

        with(AlertDialog.Builder(context)) {
            setTitle(title)
            setMessage(message)
            setCancelable(false)
            setPositiveButton(buttonPositive) { _, _ -> positive() }
            if (buttonNegative.isNotEmpty()) {
                setNegativeButton(buttonNegative) { _, _ -> negative() }
            }
            create()
            show()
        }
    }
}