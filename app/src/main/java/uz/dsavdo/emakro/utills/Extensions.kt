package uz.dsavdo.emakro.utills

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.qrcode.QRCodeWriter
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import uz.dsavdo.emakro.R
import uz.dsavdo.emakro.ui.enter.EnterActivity
import java.util.*

fun Activity.changeColorStatusBar(isChange: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val decor: View = this.window.decorView
        if (isChange) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                decor.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
        } else {
            decor.systemUiVisibility = 0
        }
    }
}

fun String.setColoredPartOfString(color: String? = null, idiom: String): SpannableString {
    val customText = SpannableString(this).apply {
        setSpan(
            ForegroundColorSpan(if (color != null) Color.parseColor(color) else Color.BLACK),
            this.indexOf(idiom),
            this.indexOf(idiom) + idiom.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return customText
}

fun Activity.setLocale(languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val resources: Resources = this.resources
    val config: Configuration = resources.configuration
    config.setLocale(locale)
    resources.updateConfiguration(config, resources.displayMetrics)
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

val Number.toPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )

fun Long.getQrCodeBitmap(context: Context): Bitmap {
    val size = 512 //pixels
    val qrCodeContent = this.toString()
    val hints = hashMapOf<EncodeHintType, Int>().also {
        it[EncodeHintType.MARGIN] = 1
    } // Make the QR code buffer border narrower
    val bits = QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, size, size)
    return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
        for (x in 0 until size) {
            for (y in 0 until size) {
                it.setPixel(
                    x,
                    y,
                    when {
                        bits[x, y] -> Color.BLACK
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                            context.resources.getColor(
                                R.color.background,
                                context.theme
                            )
                        }
                        else -> {
                            context.resources.getColor(
                                R.color.background
                            )
                        }
                    }
                )
            }
        }
    }
}

fun Long.createLinearBarcodeBitmap(context: Context): Bitmap {
    val multiFormatWriter = MultiFormatWriter()
    val size = 512 //pixels
    val bitMatrix = multiFormatWriter.encode(this.toString(), BarcodeFormat.CODE_128, size, size)
    return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
        for (x in 0 until size) {
            for (y in 0 until size) {
                it.setPixel(
                    x,
                    y,
                    when {
                        bitMatrix[x, y] -> Color.BLACK
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                            context.resources.getColor(
                                R.color.background,
                                context.theme
                            )
                        }
                        else -> {
                            context.resources.getColor(
                                R.color.background
                            )
                        }
                    }
                )
            }
        }
    }

}

fun EditText.setMaskOn(button: Button? = null, activity: Activity?, textView: TextView? = null) {
    var phoneNumber = ""
    MaskedTextChangedListener.installOn(
        this,
        "+998 [00] [000]-[00]-[00]",
        emptyList(),
        AffinityCalculationStrategy.PREFIX,
        object : MaskedTextChangedListener.ValueListener {
            override fun onTextChanged(
                maskFilled: Boolean,
                extractedValue: String,
                formattedValue: String,
            ) {
                phoneNumber = extractedValue

                val isCorrect = phoneNumber.length == 9
                button?.isEnabled = isCorrect
                textView?.isVisible = !isCorrect

                if (isCorrect) {
                    closeKeyboard(activity)
                }
            }
        }
    )
}

fun EditText.getMaskedPhoneWithoutSpace(): String {
    var phone = this.text.toString()
    if (phone.startsWith("+"))
        phone = phone.substring(1, phone.length)
    return phone.replace(" ", "").replace("-", "").removeRange(0, 3)
}

fun View?.closeKeyboard(activity: Activity?) {
    if (this != null) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.windowToken, 0)
    }
}

fun Fragment.showSnackbar(snackbarText: String, timeLength: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(requireView(), snackbarText, timeLength)
        .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.black_green))
        .setActionTextColor(ContextCompat.getColor(requireContext(), android.R.color.white)).show()
}

fun Fragment.showSnackbarWithMargin(snackbarText: String, timeLength: Int = Snackbar.LENGTH_SHORT) {
    val view = requireView()
    val snackbar = Snackbar.make(view, snackbarText, timeLength)
    val snackBarView: View = snackbar.view
    val params = snackBarView.layoutParams as FrameLayout.LayoutParams

    params.setMargins(
        params.leftMargin + 48,
        params.topMargin,
        params.rightMargin + 48,
        params.bottomMargin + 48
    )

    snackBarView.layoutParams = params

    snackBarView.translationY = -(convertDpToPixel(48f, requireActivity()));
    snackbar.setBackgroundTint(
        ContextCompat.getColor(
            requireContext(),
            R.color.green
        )
    )
        .setActionTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
    snackbar.show()
}

fun Fragment.showSnackbarAuth(
    snackbarText: String,
    timeLength: Int = Snackbar.LENGTH_SHORT,
    fab: FloatingActionButton? = null,
) {
    Snackbar.make(requireView(), snackbarText, timeLength)
        .setAnchorView(fab)
        .setAction(resources.getString(R.string.text_auth_snack)) {
            startActivity(Intent(requireContext(), EnterActivity::class.java))
        }
        .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.green))
        .setActionTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
        .show()
}

fun convertDpToPixel(dp: Float, context: Context): Float {
    return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun Fragment.getDialogProgressBar(): AlertDialog.Builder {
    var builder: AlertDialog.Builder? = null

    if (builder == null) {
        builder = AlertDialog.Builder(this.requireContext())
        val progressBar = ProgressBar(this.requireContext())
        val lp: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        progressBar.layoutParams = lp
        builder.setView(progressBar)
    }
    return builder
}

fun Context.getDialogProgressBar(): AlertDialog.Builder {
    var builder: AlertDialog.Builder? = null

    if (builder == null) {
        builder = AlertDialog.Builder(this)
        val progressBar = ProgressBar(this)
        val lp: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        progressBar.layoutParams = lp
        builder.setView(progressBar)
    }
    return builder
}

