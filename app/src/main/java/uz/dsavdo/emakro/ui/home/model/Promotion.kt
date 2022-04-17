package uz.dsavdo.emakro.ui.home.model

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import uz.dsavdo.emakro.R

class Promotion(
    val id:Int
):AbstractItem<Promotion.VH>() {

    override var identifier: Long
        get() = id.toLong()
        set(value) {}

    inner class VH(itemView:View):FastAdapter.ViewHolder<Promotion>(itemView){
        override fun bindView(item: Promotion, payloads: List<Any>) {

        }

        override fun unbindView(item: Promotion) {

        }

    }

    override val type: Int
        get() = 0
    override val layoutRes: Int
        get() = R.layout.item_promotion

    override fun getViewHolder(v: View): VH  = VH(v)
}