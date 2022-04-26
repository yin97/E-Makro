package uz.dsavdo.emakro.ui.main.home.model

import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import uz.dsavdo.emakro.R
import uz.dsavdo.emakro.databinding.ItemDailyProductBinding
import uz.dsavdo.emakro.utills.toPx

enum class ViewType {
    MAIN, ALL
}

class DailyProduct(
    val id: Int,
    val itemType: ViewType
) : AbstractItem<DailyProduct.VH>() {

    override var identifier: Long
        get() = id.toLong()
        set(value) {}

    inner class VH(itemView: View) : FastAdapter.ViewHolder<DailyProduct>(itemView) {
        override fun bindView(item: DailyProduct, payloads: List<Any>) {
            val binding = ItemDailyProductBinding.bind(itemView)
            val lp = binding.consDaily.layoutParams
            when (item.itemType) {
                ViewType.MAIN -> lp.width = 120.toPx.toInt()
                ViewType.ALL -> lp.width = MATCH_PARENT
            }
            binding.consDaily.layoutParams = lp
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