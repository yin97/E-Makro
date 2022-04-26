package uz.dsavdo.emakro.ui.enter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import uz.dsavdo.emakro.databinding.ActivityEnterBinding

class EnterActivity : AppCompatActivity() {

    lateinit var binding: ActivityEnterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

    }
}