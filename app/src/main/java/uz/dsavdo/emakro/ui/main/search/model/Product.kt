package uz.dsavdo.emakro.ui.main.search.model

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import uz.dsavdo.emakro.R
import uz.dsavdo.emakro.databinding.ItemProductBinding
import uz.dsavdo.emakro.utills.setColoredPartOfString

data class Product(
    val id: Int,
    val name: String? = null,
    val description: String? = null,
    val image: String? = null,
    val price: Int? = null
) : AbstractItem<Product.VH>() {
    inner class VH(itemView: View) : FastAdapter.ViewHolder<Product>(itemView) {
        override fun bindView(item: Product, payloads: List<Any>) {
            val binding = ItemProductBinding.bind(itemView)
            binding.productPrice.text = "200 000 sum".setColoredPartOfString(color = "#008000", idiom = "200 000")
        }

        override fun unbindView(item: Product) {

        }

    }


    override val type: Int
        get() = 0
    override val layoutRes: Int
        get() = R.layout.item_product

    override fun getViewHolder(v: View): VH = VH(v)
}