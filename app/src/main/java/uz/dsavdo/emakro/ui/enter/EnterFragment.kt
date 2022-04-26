package uz.dsavdo.emakro.ui.enter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.dsavdo.emakro.R
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

        viewModel.responseCheckPhone.observe(viewLifecycleOwner) { res ->
            when (res.nextStage) {
                2 -> {
                    if (res.message == "success") {
                        findNavController().navigate(R.id.checkSmsFragment)
                    }
                }
                4 -> {
                    if (res.message == "success") {
                        findNavController().navigate(R.id.screenLockFragment)
                    }
                }
            }
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