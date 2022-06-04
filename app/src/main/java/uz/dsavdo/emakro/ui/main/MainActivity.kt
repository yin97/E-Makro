package uz.dsavdo.emakro.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.dsavdo.emakro.R
import uz.dsavdo.emakro.databinding.ActivityMainBinding
import uz.dsavdo.emakro.network.Constants.Companion.LOGIN_OFF
import uz.dsavdo.emakro.ui.enter.EnterActivity
import uz.dsavdo.emakro.utills.SharedPrefs
import uz.dsavdo.emakro.utills.changeColorStatusBar


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val navController by lazy(LazyThreadSafetyMode.NONE) {
        Navigation.findNavController(
            this,
            R.id.nav_host_fragment_activity_main
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.changeColorStatusBar(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.navigationBarColor = resources.getColor(R.color.navigation, this.theme)
        }

        binding.fabScan.setOnClickListener {
            openDialog()
        }

        binding.navView.background = null
        binding.navView.setupWithNavController(navController)
    }

    private fun openDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.dialogTitle)
        builder.setMessage(R.string.dialogMessage)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton(resources.getString(R.string.yes)) { dialogInterface, which ->
            SharedPrefs(this).setStatusLogin(LOGIN_OFF)
            val myIntent = Intent(this, EnterActivity::class.java)
            startActivity(myIntent)
            dialogInterface.dismiss()
            finish()
        }
        builder.setNegativeButton(resources.getString(R.string.no)) { dialogInterface, which ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}