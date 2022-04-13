package uz.dsavdo.emakro.utills

import android.app.Activity
import android.os.Build
import android.view.View

fun Activity.changeColorStatusBar(isChange: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val decor: View = this.window.decorView
        if (isChange) {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decor.systemUiVisibility = 0
        }
    }
}
