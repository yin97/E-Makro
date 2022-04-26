package uz.dsavdo.emakro

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.dsavdo.emakro.databinding.FragmentScreenLockBinding
import uz.dsavdo.emakro.ui.enter.EnterViewModel

@AndroidEntryPoint
class ScreenLockFragment : Fragment() {

    private val ZERO: String = "0" // Value cannot be changed.
    private val ONE: String = "1"
    private val TWO: String = "2"
    private val THREE: String = "3"
    private val FOUR: String = "4"
    private val FIVE: String = "5"
    private val SIX: String = "6"
    private val SEVEN: String = "7"
    private val EIGHT: String = "8"
    private val NINE: String = "9"
    private val BACK: String = "10"

    private var _binding: FragmentScreenLockBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EnterViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentScreenLockBinding.inflate(inflater, container, false)

        binding.zeroKeyboard.setOnClickListener { onNumberButtonClick(ZERO) }
        binding.firstKeyboard.setOnClickListener { onNumberButtonClick(ONE) }
        binding.secondKeyboard.setOnClickListener { onNumberButtonClick(TWO) }
        binding.thirdKeyboard.setOnClickListener { onNumberButtonClick(THREE) }
        binding.fourthKeyboard.setOnClickListener { onNumberButtonClick(FOUR) }
        binding.fifthKeyboard.setOnClickListener { onNumberButtonClick(FIVE) }
        binding.sixthKeyboard.setOnClickListener { onNumberButtonClick(SIX) }
        binding.seventhKeyboard.setOnClickListener { onNumberButtonClick(SEVEN) }
        binding.eightKeyboard.setOnClickListener { onNumberButtonClick(EIGHT) }
        binding.ninethKeyboard.setOnClickListener { onNumberButtonClick(NINE) }
        binding.backKeyboard.setOnClickListener { onNumberButtonClick(BACK) }

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun onNumberButtonClick(number: String) {

        val currentValue: String = binding.etPinCodeNumber.text.toString()

        if (number != "10") {
            if (currentValue.length != 4) {
                binding.etPinCodeNumber.setText("${currentValue}${number}")
                if (binding.etPinCodeNumber.text.toString().length == 4)
                    viewModel.login(this,viewModel.phoneNumber, binding.etPinCodeNumber.text.toString())
            }
        } else {
            if (currentValue.length in 1..4) {
                binding.etPinCodeNumber.setText(
                    currentValue.substring(
                        0,
                        currentValue.length - 1
                    )
                )
            }
        }

    }
}