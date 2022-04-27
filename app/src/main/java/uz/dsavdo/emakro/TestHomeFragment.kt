package uz.dsavdo.emakro

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.dsavdo.emakro.databinding.FragmentTestHomeBinding
import uz.dsavdo.emakro.network.Constants.Companion.LOGIN_ON
import uz.dsavdo.emakro.ui.enter.EnterViewModel
import uz.dsavdo.emakro.utills.EAN13CodeBuilder
import uz.dsavdo.emakro.utills.SharedPrefs
import uz.dsavdo.emakro.utills.getQrCodeBitmap


@AndroidEntryPoint
class TestHomeFragment : Fragment() {

    private var _binding: FragmentTestHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EnterViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (SharedPrefs(requireContext()).statusOfLogin == LOGIN_ON) {
            val phone = SharedPrefs(requireContext()).phone
            viewModel.getCard(this, phone.substring(3, phone.length))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTestHomeBinding.inflate(inflater, container, false)

        viewModel.responseGetCard.observe(viewLifecycleOwner) { res ->
            binding.qrCode.setImageBitmap(res.artix_id?.toLong()?.getQrCodeBitmap(requireContext()))

            val bb = EAN13CodeBuilder(res.artix_id.toString())
            binding.tvLinearBarcode.setText(bb.code)
        }

        return binding.root
    }

}