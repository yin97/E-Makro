package uz.dsavdo.emakro.ui.enter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.AndroidEntryPoint
import uz.dsavdo.emakro.app.MainApplication
import uz.dsavdo.emakro.databinding.FragmentEnterBinding
import uz.dsavdo.emakro.network.Constants.Companion.BASE_URL
import uz.dsavdo.emakro.utills.getMaskedPhoneWithoutSpace
import uz.dsavdo.emakro.utills.setMaskOn

@AndroidEntryPoint
class EnterFragment : Fragment() {

    private var _binding: FragmentEnterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EnterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEnterBinding.inflate(inflater, container, false)

        binding.etRegistrationNumber.setMaskOn(button = binding.btnNext, activity = activity)

        binding.btnNext.setOnClickListener {
            viewModel.checkPhone(this, binding.etRegistrationNumber.getMaskedPhoneWithoutSpace())
        }

        binding.tvOferta.setOnClickListener {
            val url = "${BASE_URL}pages/usloviya"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        binding.languageUz.setOnClickListener {
            it.isSelected = true
            binding.languageRu.isSelected = false
            binding.languageEng.isSelected = false
            changeLanguage(MainApplication.LANGUAGE_UZBEKISTAN)
        }

        binding.languageRu.setOnClickListener {
            it.isSelected = true
            binding.languageUz.isSelected = false
            binding.languageEng.isSelected = false
            changeLanguage(MainApplication.LANGUAGE_RUSSIAN)
        }

        binding.languageEng.setOnClickListener {
            it.isSelected = true
            binding.languageUz.isSelected = false
            binding.languageRu.isSelected = false
            changeLanguage(MainApplication.LANGUAGE_ENGLISH)
        }

        when (Lingver.getInstance().getLanguage()) {
            MainApplication.LANGUAGE_RUSSIAN -> {
                binding.languageRu.isSelected = true
                binding.languageUz.isSelected = false
                binding.languageEng.isSelected = false
            }
            MainApplication.LANGUAGE_UZBEKISTAN -> {
                binding.languageUz.isSelected = true
                binding.languageRu.isSelected = false
                binding.languageEng.isSelected = false
            }
            MainApplication.LANGUAGE_ENGLISH -> {
                binding.languageEng.isSelected = true
                binding.languageUz.isSelected = false
                binding.languageRu.isSelected = false
            }
        }


        return binding.root
    }

    private fun changeLanguage(lang: String) {
        Lingver.getInstance().setLocale(requireContext(), lang)
        startActivity(Intent(requireContext(), EnterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }


}