package uz.dsavdo.emakro.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.model.CarouselType
import uz.dsavdo.emakro.R
import uz.dsavdo.emakro.databinding.FragmentHomeBinding
import uz.dsavdo.emakro.ui.home.model.DailyProduct
import uz.dsavdo.emakro.ui.home.model.News
import uz.dsavdo.emakro.ui.home.model.Promotion

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val homeViewModel: HomeViewModel by viewModels()
    private val binding get() = _binding!!

    private val itemAdapter = ItemAdapter<DailyProduct>()
    private val fastAdapter = FastAdapter.with(itemAdapter)

    private val pItemAdapter = ItemAdapter<Promotion>()
    private val pFastAdapter = FastAdapter.with(pItemAdapter)

    private val nItemAdapter = ItemAdapter<News>()
    private val nFastAdapter = FastAdapter.with(nItemAdapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.addData()
        homeViewModel.addPromotion()
        homeViewModel.addNews()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root

        startCarouselView()

        homeViewModel.list.observe(viewLifecycleOwner) { products ->
            binding.dailyProductList.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            binding.dailyProductList.adapter = fastAdapter
            itemAdapter.clear()
            itemAdapter.add(products)
        }

        homeViewModel.promotions.observe(viewLifecycleOwner) { promotions ->
            binding.promotionsList.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            binding.promotionsList.adapter = pFastAdapter
            pItemAdapter.clear()
            pItemAdapter.add(promotions)
        }

        homeViewModel.news.observe(viewLifecycleOwner){news->
            binding.newsList.layoutManager =
                LinearLayoutManager(requireContext())
            binding.newsList.adapter = nFastAdapter
            nItemAdapter.clear()
            news.forEach { e->
                if (e.id<=3){
                    nItemAdapter.add(e)
                }
            }
        }



        return root
    }

    private fun startCarouselView() {
        binding.carousel.registerLifecycle(viewLifecycleOwner)
        binding.carousel2.registerLifecycle(viewLifecycleOwner)
        val list = mutableListOf<CarouselItem>()
        val list2 = mutableListOf<CarouselItem>()
        repeat(5) {
            list.add(CarouselItem(R.drawable.test_image))
            list2.add(CarouselItem(R.drawable.ekzo_back))
        }
        binding.apply {
            carousel.apply {
                carouselType = CarouselType.SHOWCASE
                scaleOnScroll = true
                autoPlay = true
                showIndicator = true
                showNavigationButtons = false
                setData(list)
            }
            carousel2.apply {
                carouselType = CarouselType.SHOWCASE
                scaleOnScroll = true
                autoPlay = true
                showIndicator = true
                showNavigationButtons = false
                setData(list2)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}