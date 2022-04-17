package uz.dsavdo.emakro.ui.home.model

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import uz.dsavdo.emakro.R

class News(
    val id: Int
) : AbstractItem<News.VH>() {

    override var identifier: Long
        get() = id.toLong()
        set(value) {}

    inner class VH(itemView: View) : FastAdapter.ViewHolder<News>(itemView) {
        override fun bindView(item: News, payloads: List<Any>) {

        }

        override fun unbindView(item: News) {

        }

    }

    override val type: Int
        get() = 0
    override val layoutRes: Int
        get() = R.layout.item_news

    override fun getViewHolder(v: View): VH = VH(v)
}