package uz.dsavdo.emakro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.dsavdo.emakro.databinding.FragmentCheckSmsBinding
import uz.dsavdo.emakro.ui.enter.EnterViewModel
import uz.dsavdo.emakro.utills.getMaskedPhoneWithoutSpace
import uz.dsavdo.emakro.utills.setMaskOn

@AndroidEntryPoint
class CheckSmsFragment : Fragment() {

    private var _binding: FragmentCheckSmsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EnterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCheckSmsBinding.inflate(inflater, container, false)

        binding.etRegistrationSmsCode.setMaskOn(button = binding.btnNext, activity = activity)

        binding.apply {
            btnNext.setOnClickListener {
                if (etRegistrationSmsCode.text?.isNotEmpty() == true){
                    viewModel.checkSms(
                        this@CheckSmsFragment,
                        viewModel.phoneNumber,
                        etRegistrationSmsCode.getMaskedPhoneWithoutSpace()
                    )
                }else{
                    etRegistrationSmsCode.error = resources.getString(R.string.text_error)
                }
            }
        }

        return binding.root
    }

}