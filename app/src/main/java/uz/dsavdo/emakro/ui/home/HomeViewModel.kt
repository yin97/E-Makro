package uz.dsavdo.emakro.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.model.CarouselType
import uz.dsavdo.emakro.R
import uz.dsavdo.emakro.databinding.FragmentHomeBinding
import uz.dsavdo.emakro.ui.home.model.DailyProduct
import uz.dsavdo.emakro.ui.home.model.News
import uz.dsavdo.emakro.ui.home.model.Promotion

class HomeViewModel : ViewModel() {

    val list = MutableLiveData<ArrayList<DailyProduct>>()
    val promotions = MutableLiveData<ArrayList<Promotion>>()
    val news = MutableLiveData<ArrayList<News>>()

    fun addData() {
        val products = ArrayList<DailyProduct>()

        repeat(15) { id ->
            products.add(DailyProduct(id))

        }

        list.postValue(products)

    }

    fun addPromotion() {
        val promotions = ArrayList<Promotion>()

        repeat(15) { id ->
            promotions.add(Promotion(id))
        }

        this.promotions.postValue(promotions)
    }

    fun addNews(){
        val news = ArrayList<News>()

        repeat(15){id->
            news.add(News(id))
        }

        this.news.postValue(news)
    }


}