package uz.dsavdo.emakro.ui.home.model

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import uz.dsavdo.emakro.R

class DailyProduct(
    val id: Int
) : AbstractItem<DailyProduct.VH>() {

    override var identifier: Long
        get() = id.toLong()
        set(value) {}

    inner class VH(itemView: View) : FastAdapter.ViewHolder<DailyProduct>(itemView) {
        override fun bindView(item: DailyProduct, payloads: List<Any>) {

        }

        override fun unbindView(item: DailyProduct) {

        }

    }

    override val type: Int
        get() = 0
    override val layoutRes: Int
        get() = R.layout.item_daily_product

    override fun getViewHolder(v: View): VH = VH(v)
}