package uz.dsavdo.emakro

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import uz.dsavdo.emakro.databinding.ActivityMainBinding
import uz.dsavdo.emakro.utills.changeColorStatusBar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
            window.navigationBarColor = resources.getColor(R.color.navigation,this.theme)
        }

        binding.navView.background = null
        binding.navView.setupWithNavController(navController)
    }
}