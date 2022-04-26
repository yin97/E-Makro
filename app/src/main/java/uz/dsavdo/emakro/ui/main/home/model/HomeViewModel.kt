package uz.dsavdo.emakro.ui.main.home.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val list = MutableLiveData<ArrayList<DailyProduct>>()
    val promotions = MutableLiveData<ArrayList<Promotion>>()
    val news = MutableLiveData<ArrayList<News>>()

    fun addData(itemType: ViewType) {
        val products = ArrayList<DailyProduct>()

        repeat(15) { id ->
            products.add(DailyProduct(id,itemType))

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