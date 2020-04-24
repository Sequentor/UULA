package ru.sequentor.uula.ui.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import kotlinx.android.synthetic.main.activity_main.*
import ru.sequentor.uula.R
import ru.sequentor.uula.utils.initGlide

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Set transparent statusbar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        // Set Image to Collapsing Toolbar
        initGlide(app_bar_bg, AppCompatResources.getDrawable(this, R.drawable.app_bar_bg))
    }
}
