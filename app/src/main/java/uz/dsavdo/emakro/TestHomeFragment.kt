package uz.dsavdo.emakro

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.qrcode.QRCodeWriter
import dagger.hilt.android.AndroidEntryPoint
import uz.dsavdo.emakro.databinding.FragmentTestHomeBinding
import uz.dsavdo.emakro.network.Constants.Companion.LOGIN_ON
import uz.dsavdo.emakro.ui.enter.EnterViewModel
import uz.dsavdo.emakro.utills.*


@AndroidEntryPoint
class TestHomeFragment : Fragment() {

    private var _binding: FragmentTestHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EnterViewModel by viewModels()

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
            try {
                binding.qrCode.setImageBitmap(
                    res.artix_id?.toLong()?.getQrCodeBitmap(requireContext())
                )

                binding.tvLinearBarcode.setImageBitmap(
                    res.artix_id?.toLong()?.createLinearBarcodeBitmap(requireContext())
                )
                val amount = res.amount?.toFloat()?.div(100).toString()
                val count = amount.substring(0, amount.length - 3)

                binding.tvBalance.text = resources.getString(
                    R.string.tv_balance,
                    if (count != "") count else "0"
                ).setColoredPartOfString("#2695FC", count.toString())

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return binding.root
    }

}