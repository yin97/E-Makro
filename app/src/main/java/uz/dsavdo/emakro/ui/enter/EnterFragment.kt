package uz.dsavdo.emakro.ui.enter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import uz.dsavdo.emakro.R
import uz.dsavdo.emakro.databinding.FragmentEnterBinding
import uz.dsavdo.emakro.utills.getMaskedPhoneWithoutSpace
import uz.dsavdo.emakro.utills.setMaskOn

class EnterFragment : Fragment() {

    private var _binding: FragmentEnterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEnterBinding.inflate(inflater, container, false)

        binding.etRegistrationNumber.setMaskOn(button = binding.btnNext, activity = activity)

        binding.btnNext.setOnClickListener {
            Toast.makeText(
                requireContext(),
                binding.etRegistrationNumber.getMaskedPhoneWithoutSpace(),
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.languageUz.setOnClickListener {
            it.isSelected = true
            binding.languageRu.isSelected = false
            binding.languageEng.isSelected = false
        }

        binding.languageRu.setOnClickListener {
            it.isSelected = true
            binding.languageUz.isSelected = false
            binding.languageEng.isSelected = false
        }

        binding.languageEng.setOnClickListener {
            it.isSelected = true
            binding.languageUz.isSelected = false
            binding.languageRu.isSelected = false
        }

        return binding.root
    }

}