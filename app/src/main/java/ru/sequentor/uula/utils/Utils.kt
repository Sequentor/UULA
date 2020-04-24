package ru.sequentor.uula.utils

import android.content.res.Resources
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import ru.sequentor.uula.application.App

fun convertDpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}

fun setCompoundDrawable(textView: TextView, drawable: Int) {
    with(getDrawable(App.getContext(), drawable)) {
        this?.setBounds(0, 0, convertDpToPx(20), convertDpToPx(20))
        textView.setCompoundDrawables(this, null, null, null)
        textView.compoundDrawablePadding = convertDpToPx(8)
    }
}