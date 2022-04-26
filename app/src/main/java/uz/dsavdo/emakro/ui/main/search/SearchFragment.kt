package uz.dsavdo.emakro.ui.main.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.listeners.ClickEventHook
import uz.dsavdo.emakro.databinding.FragmentSearchBinding
import uz.dsavdo.emakro.ui.main.search.model.Product
import uz.dsavdo.emakro.ui.main.search.model.SearchViewModel
import uz.dsavdo.emakro.utills.afterTextChanged

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val itemAdapter = ItemAdapter<Product>()
    private val fastAdapter =
        FastAdapter.with(itemAdapter).addEventHook(object : ClickEventHook<Product>() {
            override fun onClick(
                v: View,
                position: Int,
                fastAdapter: FastAdapter<Product>,
                item: Product
            ) {

            }

            override fun onBindMany(viewHolder: RecyclerView.ViewHolder): List<View>? {
                return super.onBindMany(viewHolder)
            }

        })
    private val vm: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.searchResultList.layoutManager = LinearLayoutManager(requireContext())
        binding.searchResultList.adapter = fastAdapter


        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.etSearch.afterTextChanged {
            when (it) {
                "apple" -> vm.addData(5)
                "soup" -> vm.addData(10)
                "water" -> vm.addData(8)
                "" -> itemAdapter.clear()
            }
        }

        vm.searchList.observe(viewLifecycleOwner) {
            itemAdapter.clear()
            itemAdapter.add(it)
        }

        return binding.root

    }
}