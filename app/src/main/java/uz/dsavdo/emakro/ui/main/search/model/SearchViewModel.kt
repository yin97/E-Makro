package uz.dsavdo.emakro.ui.main.search.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    val searchList = MutableLiveData<ArrayList<Product>>()

    fun addData(size: Int) {
        val list = ArrayList<Product>()
        repeat(size) { id ->
            list.add(Product(id))
        }
        searchList.postValue(list)
    }

}
