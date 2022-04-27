package uz.dsavdo.emakro.ui.enter

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import uz.dsavdo.emakro.databinding.ActivityEnterBinding
import uz.dsavdo.emakro.network.Constants.Companion.LOGIN_ON
import uz.dsavdo.emakro.ui.main.MainActivity
import uz.dsavdo.emakro.utills.SharedPrefs

@AndroidEntryPoint
class EnterActivity : AppCompatActivity() {

    lateinit var binding: ActivityEnterBinding
    lateinit var viewModel: EnterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (SharedPrefs(this).statusOfLogin == LOGIN_ON) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            binding = ActivityEnterBinding.inflate(layoutInflater)
            setContentView(binding.root)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
    }
}